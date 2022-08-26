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
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.dao.impl.HotelAmenityDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
import io.swen90007sm2.app.dao.impl.HotelierDao;
import io.swen90007sm2.app.model.entity.BaseEntity;
import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.CreateHotelParam;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Blo
public class HotelBlo implements IHotelBlo {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;


    public void doCreateHotel(String hotelierId, CreateHotelParam createHotelParam) {

        // check existence, one hotelier can only create one hotel
        Hotelier currentHotelier = getHotelierFromCacheOrDb(hotelierId);
        if (StringUtils.isNotEmpty(currentHotelier.getHotelId())) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_ALREADY_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_ALREADY_HAS_HOTEL.getCode()
            );
        }

        Hotel hotel = new Hotel();
        Long id = IdFactory.genSnowFlakeId();
        hotel.setId(id);
        hotel.setHotelId(id.toString());
        hotel.setName(createHotelParam.getName());
        hotel.setDescription(createHotelParam.getDescription());
        hotel.setAddress(createHotelParam.getAddress());
        hotel.setPostCode(createHotelParam.getPostCode());
        hotel.setOnSale(createHotelParam.getOnSale());

        List<String> amenity_ids = createHotelParam.getAmenityIds();
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

    @Override
    public List<Hotel> getHotelsByPageSortedByCreateTime(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<Hotel> getHotelsByPageSortedByPrice(Integer pageNum, Integer pageSize, Integer order) {
        return null;
    }

    @Override
    public List<Hotel> getHotelsByPageSortedByRank(Integer pageNum, Integer pageSize, Integer order) {
        return null;
    }

    @Override
    public List<Hotel> searchHotelsByPageByName(Integer pageNum, Integer pageSize, String name, Integer sortBy, Integer order) {
        List<Hotel> hotels = null;
        // cache method res
        Object[] params = {name, sortBy, order};
        String methodName = "searchHotelsByPageByName";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotels = (List<Hotel>) result;
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    hotels = hotelDao.findAllByName(true, name);

                    // need to sort the result, we are using logical paging, we fetch all data from db, and sort.
                    // This is because search data has limited number
                    if (sortBy == null || order == null) {
                        // if there is no sort params come in...
                        // by default, sort by rank desc
                        hotels.sort((hotel1, hotel2)-> hotel2.getRank() - hotel1.getRank());

                    } else {
                        // sort the result, default is up and by rank
                        if (sortBy.equals(CommonConstant.SORT_BY_PRICE)) {
                            hotels.sort(Comparator.comparing(Hotel::getMinPrice));
                        } else if (sortBy.equals(CommonConstant.SORT_BY_CREATE_TIME)) {
                            hotels.sort(Comparator.comparing(Hotel::getCreateTime));
                        } else {
                            hotels.sort(Comparator.comparingInt(Hotel::getRank));
                        }

                        // if sort down, revert the list
                        if (!order.equals(CommonConstant.SORT_UP)) {
                            Collections.reverse(hotels);
                        }
                    }

                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotels,
                            RandomUtil.randomLong(CacheConstant.CACHE_HOT_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                            );

                } else {
                    hotels = (List<Hotel>) result;
                }
            }
        }

        return hotels;
    }

    @Override
    public List<Hotel> searchHotelsByPageByPostCode(Integer pageNum, Integer pageSize, String postCode, Integer sortBy, Integer order) {
        return null;
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
