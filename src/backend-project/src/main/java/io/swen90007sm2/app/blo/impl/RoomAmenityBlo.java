package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.app.blo.IRoomAmenityBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.dao.IRoomAmenityDao;
import io.swen90007sm2.app.dao.impl.RoomAmenityDao;
import io.swen90007sm2.app.model.entity.RoomAmenity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-30 13:48
 */
@Blo
public class RoomAmenityBlo implements IRoomAmenityBlo {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    @Override
    public List<RoomAmenity> getAllAmenities() {
        Optional<Object> cachedItem = cache.get(CacheConstant.CACHED_ROOM_AMENITIES);
        List<RoomAmenity> roomAmenities;
        if (cachedItem.isEmpty()) {
            synchronized (this) {
                cachedItem = cache.get(CacheConstant.CACHED_ROOM_AMENITIES);
                if (cachedItem.isEmpty()) {
                    IRoomAmenityDao amenityDao = BeanManager.getLazyBeanByClass(RoomAmenityDao.class);
                    roomAmenities = amenityDao.findAllAmenities();

                    // cache all roomAmenities
                    cache.put(
                            CacheConstant.CACHED_ROOM_AMENITIES,
                            roomAmenities,
                            RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );

                    // cache each amenities
                    roomAmenities.forEach(roomAmenity -> {
                        cache.put(
                                CacheConstant.ENTITY_ROOM_AMENITY_KEY_PREFIX,
                                roomAmenity,
                                RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });
                } else {
                    roomAmenities = (List<RoomAmenity>) cachedItem.get();
                }
            }
        } else {
            roomAmenities = (List<RoomAmenity>) cachedItem.get();
        }

        return roomAmenities;
    }

    @Override
    public RoomAmenity getRoomAmenityInfoByAmenityId(String amenityId) {
        Optional<Object> cachedItem = cache.get(CacheConstant.ENTITY_ROOM_AMENITY_KEY_PREFIX + amenityId);
        RoomAmenity roomAmenity;
        if (cachedItem.isEmpty()) {
            synchronized (this) {
                cachedItem = cache.get(CacheConstant.ENTITY_ROOM_AMENITY_KEY_PREFIX + amenityId);
                if (cachedItem.isEmpty()) {
                    IRoomAmenityDao amenityDao = BeanManager.getLazyBeanByClass(RoomAmenityDao.class);
                    roomAmenity = amenityDao.findAmenityBeAmenityId(amenityId);

                    // cache amenity info
                    cache.put(
                            CacheConstant.ENTITY_ROOM_AMENITY_KEY_PREFIX+ amenityId,
                            roomAmenity,
                            RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    roomAmenity = (RoomAmenity) cachedItem.get();
                }
            }
        } else {
            roomAmenity = (RoomAmenity) cachedItem.get();
        }

        return roomAmenity;
    }

    @Override
    public List<RoomAmenity> getAllAmenitiesByRoomId(String roomId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.CACHED_AMENITIES_FOR_ROOM + roomId);

        List<RoomAmenity> amenities = null;

        if (cacheItem.isPresent()) {
            amenities = (List<RoomAmenity>) cacheItem.get();
        } else {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.CACHED_AMENITIES_FOR_ROOM + roomId);
                if (cacheItem.isEmpty()) {
                    IRoomAmenityDao amenityDao = BeanManager.getLazyBeanByClass(RoomAmenityDao.class);
                    amenities = amenityDao.findAllAmenitiesByRoomId(roomId);

                    // cache result
                    cache.put(
                            CacheConstant.CACHED_AMENITIES_FOR_ROOM + roomId,
                            amenities,
                            CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX,
                            TimeUnit.MILLISECONDS
                    );

                    // cache amenities
                    amenities.forEach(
                            roomAmenity -> {
                                cache.putIfAbsent(
                                        CacheConstant.ENTITY_ROOM_AMENITY_KEY_PREFIX + roomAmenity.getAmenityId(),
                                        roomAmenity,
                                        RandomUtil.randomLong(CacheConstant.CACHE_COLD_EXPIRATION_PERIOD_MAX),
                                        TimeUnit.MILLISECONDS
                                );
                            }
                    );
                } else {
                    amenities = (List<RoomAmenity>) cacheItem.get();
                }
            }
        }

        return amenities;
    }

    @Override
    public void updateAmenityIdsForRoom(List<String> amenityIds, String roomId) {
        IRoomAmenityDao amenityDao = BeanManager.getLazyBeanByClass(RoomAmenityDao.class);
        // atom operations
        synchronized (this) {
            // clean associate table first, then insert
            amenityDao.clearAmenityIdsForRoom(roomId);

            if (amenityIds != null) {
                amenityDao.addAmenityIdsToRoom(amenityIds, roomId);
            }

            // clear cache
            cache.remove(CacheConstant.VO_ROOM_KEY_PREFIX + roomId);

            // clear related method cache
            cache.remove(CacheConstant.CACHED_AMENITIES_FOR_ROOM + roomId);
        }
    }
}