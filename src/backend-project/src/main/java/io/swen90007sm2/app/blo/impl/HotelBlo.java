package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.dao.impl.HotelAmenityDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
import io.swen90007sm2.app.dao.impl.HotelierDao;
import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.CreateHotelParam;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Blo
public class HotelBlo implements IHotelBlo {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;


    public void doCreateHotel(String hotelierId, CreateHotelParam createHotelParam) {
        Hotel hotel = new Hotel();
        hotel.setId(IdFactory.genSnowFlakeId());
        hotel.setHotelId(IdFactory.genSnowFlaskIdString());
        hotel.setName(createHotelParam.getName());
        hotel.setDescription(createHotelParam.getDescription());
        hotel.setAddress(createHotelParam.getAddress());
        hotel.setPostCode(createHotelParam.getPostCode());
        hotel.setOnSale(createHotelParam.getOnSale());

        List<String> amenity_ids = createHotelParam.getAmenity_ids();
        IHotelAmenityDao amenityDao = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);

        // get cooresponding hotelier info
        Hotelier hotelier = getHotelierFromCacheOrDb(hotelierId);
        hotelier.setHotelId(hotel.getHotelId());

        synchronized (this) {
            // firstly, insert new hotel
            hotelDao.insertOne(hotel);

            // update hotelier info
            hotelierDao.updateOne(hotelier);
            cache.remove(CacheConstant.ENTITY_USER_KEY_PREFIX + hotelierId);

            // attach amnities to this hotel
            amenityDao.addAmenityIdsToHotel(
                    amenity_ids,
                    hotel.getHotelId()
            );
        }
    }

    private Hotelier getHotelierFromCacheOrDb(String userId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_USER_KEY_PREFIX + userId);
        Hotelier hotelier = null;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.ENTITY_USER_KEY_PREFIX + userId);
                // if the cache is still empty
                if (cacheItem.isEmpty()) {
                    IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
                    hotelier = hotelierDao.findOneByBusinessId(userId);
                    // if no such customer
                    if (hotelier == null) {
                        throw new RequestException(
                                StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                                StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
                    }

                    // use randomed expiration time to prevent Cache Avalanche
                    cache.put(
                            CacheConstant.ENTITY_USER_KEY_PREFIX + userId,
                            hotelier,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    // data is put by other thread, just get from cache.
                    hotelier = (Hotelier) cacheItem.get();
                }
            }

        } else {
            hotelier = (Hotelier) cacheItem.get();
        }
        return hotelier;
    }
}
