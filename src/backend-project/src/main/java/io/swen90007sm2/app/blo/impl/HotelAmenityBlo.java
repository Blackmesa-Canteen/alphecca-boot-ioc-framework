package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.app.blo.IHotelAmenityBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.dao.impl.HotelAmenityDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
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

                    // cache each amenities
                    hotelAmenities.forEach(hotelAmenity -> {
                        // cache hotel amenity info
                        cache.put(
                                CacheConstant.ENTITY_HOTEL_AMENITY_KEY_PREFIX + hotelAmenity.getAmenityId(),
                                hotelAmenity,
                                RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });

                } else {
                    hotelAmenities =  (List<HotelAmenity>) cachedItem.get();
                }
            }
        } else {
            hotelAmenities = (List<HotelAmenity>) cachedItem.get();
        }

        return hotelAmenities;
    }

    @Override
    public HotelAmenity getHotelAmenityInfoByAmenityId(String amenityId) {
        Optional<Object> cachedItem = cache.get(CacheConstant.ENTITY_HOTEL_AMENITY_KEY_PREFIX + amenityId);
        HotelAmenity hotelAmenity;
        if (cachedItem.isEmpty()) {
            synchronized (this) {
                cachedItem = cache.get(CacheConstant.ENTITY_HOTEL_AMENITY_KEY_PREFIX + amenityId);
                if (cachedItem.isEmpty()) {
                    IHotelAmenityDao amenityDao = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
                    hotelAmenity = amenityDao.findOneAmenityByAmenityId(amenityId);

                    // cache amenity info
                    cache.put(
                            CacheConstant.ENTITY_HOTEL_AMENITY_KEY_PREFIX+ amenityId,
                            hotelAmenity,
                            RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    hotelAmenity = (HotelAmenity) cachedItem.get();
                }
            }
        } else {
            hotelAmenity = (HotelAmenity) cachedItem.get();
        }

        return hotelAmenity;
    }

    @Override
    public void updateAmenityIdsForHotel(List<String> amenityIds, String hotelId) {
        IHotelAmenityDao amenityDao = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
        // atom operation
        synchronized (this) {

            // update amenities: cleanup associate table, and add all again
            amenityDao.clearAmenityIdsForHotel(hotelId);

            if (amenityIds != null) {
                amenityDao.addAmenityIdsToHotel(amenityIds, hotelId);
            }

            // clean up cache
            cache.remove(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);

            // clean up a related method cache
            String methodName = "getAllAmenitiesByHotelId";
            Object[] params = {hotelId};
            CacheUtil.clearMethodResultInCache(methodName, params);
        }
    }

    @Override
    public List<HotelAmenity> getAllAmenitiesByHotelId(String hotelId) {
        List<HotelAmenity> amenities = null;
        // cache method res
        Object[] params = {hotelId};
        String methodName = "getAllAmenitiesByHotelId";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);

        if (result != null) {
            amenities = (List<HotelAmenity>) result;
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    IHotelAmenityDao amenityDao = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
                    amenities = amenityDao.findAllAmenitiesByHotelId(hotelId);

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            amenities,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                    // cache amenities
                    amenities.forEach(amenity -> {
                        cache.put(
                                CacheConstant.ENTITY_HOTEL_AMENITY_KEY_PREFIX + amenity.getAmenityId(),
                                amenity,
                                RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });
                } else {
                    amenities = (List<HotelAmenity>) result;
                }
            }
        }

        return amenities;
    }
}