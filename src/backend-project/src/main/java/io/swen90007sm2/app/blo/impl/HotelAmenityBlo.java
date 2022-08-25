package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.app.blo.IHotelAmenityBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.dao.impl.HotelAmenityDao;
import io.swen90007sm2.app.model.entity.HotelAmenity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 */
@Blo
public class HotelAmenityBlo implements IHotelAmenityBlo {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    @Override
    public List<HotelAmenity> getAllAmenities() {

        Optional<Object> cachedItem = cache.get(CacheConstant.CACHED_HOTEL_AMENITIES);
        List<HotelAmenity> hotelAmenities;
        if (cachedItem.isEmpty()) {
            synchronized (this) {
                cachedItem = cache.get(CacheConstant.CACHED_HOTEL_AMENITIES);
                if (cachedItem.isEmpty()) {
                    IHotelAmenityDao amenityDao = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
                    hotelAmenities = amenityDao.findAllAmenities();

                    // hotelAmenities tends to be fixed, so have longer expiration time
                    cache.put(
                            CacheConstant.CACHED_HOTEL_AMENITIES,
                            hotelAmenities,
                            RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    hotelAmenities =  (List<HotelAmenity>) cachedItem.get();
                }
            }
        } else {
            hotelAmenities = (List<HotelAmenity>) cachedItem.get();
        }

        return hotelAmenities;
    }
}