package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelBlo;
import io.swen90007sm2.app.blo.IRoomAmenityBlo;
import io.swen90007sm2.app.blo.IRoomBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.dao.IRoomAmenityDao;
import io.swen90007sm2.app.dao.IRoomDao;
import io.swen90007sm2.app.dao.impl.RoomAmenityDao;
import io.swen90007sm2.app.dao.impl.RoomDao;
import io.swen90007sm2.app.model.entity.Room;
import io.swen90007sm2.app.model.entity.RoomAmenity;
import io.swen90007sm2.app.model.param.CreateRoomParam;
import io.swen90007sm2.app.model.param.UpdateRoomParam;
import io.swen90007sm2.app.model.pojo.Money;
import io.swen90007sm2.app.model.vo.HotelVo;
import io.swen90007sm2.app.model.vo.RoomVo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 */

@Blo
public class RoomBlo implements IRoomBlo {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    @AutoInjected
    IHotelBlo hotelBlo;

    @AutoInjected
    IRoomAmenityBlo roomAmenityBlo;

    @Override
    public void doCreateRoomToHotel(CreateRoomParam param) {
        HotelVo hotel = hotelBlo.getHotelInfoByHotelId(param.getHotelId());
        if (hotel == null) {
            throw new RequestException(
                    "hotel not found.",
                    StatusCodeEnume.RESOURCE_NOT_FOUND_EXCEPTION.getCode()
            );
        }

        Room room = new Room();
        Long id = IdFactory.genSnowFlakeId();
        room.setHotelId(hotel.getHotelId());
        room.setId(id);
        room.setRoomId(id.toString());
        room.setName(param.getName());
        room.setDescription(param.getDescription());
        room.setPricePerNight(param.getPricePerNight());
        room.setCurrency(param.getCurrency());
        room.setSleepsNum(param.getSleepsNum());
        room.setVacantNum(param.getVacantNum());
        room.setOnSale(param.getOnSale());

        List<String> amenity_ids = param.getAmenityIds();

        IRoomAmenityDao roomAmenityDao = BeanManager.getLazyBeanByClass(RoomAmenityDao.class);
        IRoomDao roomDao = BeanManager.getLazyBeanByClass(RoomDao.class);

        // atom operations
        synchronized (this) {
            // insert new room
            roomDao.insertOne(room);

            // attach many-to-many associate table of amenity
            roomAmenityDao.addAmenityIdsToRoom(
                    amenity_ids,
                    room.getRoomId()
            );
        }
    }

    @Override
    public void updateRoomByRoomId(UpdateRoomParam param) {
        String roomId = param.getRoomId();

        Room originalRoomObj = getRoomEntityByRoomId(roomId);
        Room newRoomObj = new Room();
        BeanUtil.copyProperties(originalRoomObj, newRoomObj);

        newRoomObj.setName(param.getName());
        newRoomObj.setDescription(param.getDescription());
        newRoomObj.setPricePerNight(param.getPricePerNight());
        newRoomObj.setCurrency(param.getCurrency());
        newRoomObj.setSleepsNum(param.getSleepsNum());
        newRoomObj.setVacantNum(param.getVacantNum());
        newRoomObj.setOnSale(param.getOnSale());

        IRoomDao roomDao = BeanManager.getLazyBeanByClass(RoomDao.class);
        synchronized (this) {
            roomDao.insertOne(newRoomObj);
            roomAmenityBlo.updateAmenityIdsForRoom(param.getAmenityIds(), roomId);

            cache.remove(CacheConstant.VO_ROOM_KEY_PREFIX + roomId);
            cache.remove(CacheConstant.ENTITY_ROOM_KEY_PREFIX + roomId);
        }
        // don't forget to clean cache after updating
    }

    @Override
    public Room getRoomEntityByRoomId(String roomId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_ROOM_KEY_PREFIX + roomId);
        Room room = null;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.ENTITY_ROOM_KEY_PREFIX + roomId);
                if (cacheItem.isEmpty()) {
                    IRoomDao roomDao = BeanManager.getLazyBeanByClass(RoomDao.class);
                    room = roomDao.findOneByBusinessId(roomId);
                    if (room == null) {
                        throw new RequestException(
                                "room not found",
                                StatusCodeEnume.RESOURCE_NOT_FOUND_EXCEPTION.getCode());
                    }

                    cache.put(
                            CacheConstant.ENTITY_ROOM_KEY_PREFIX + roomId,
                            room,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    room = (Room) cacheItem.get();
                }
            }
        } else {
            room = (Room) cacheItem.get();
        }

        return room;
    }

    @Override
    public RoomVo getRoomInfoByRoomId(String roomId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.VO_ROOM_KEY_PREFIX + roomId);
        RoomVo roomVo = null;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.VO_ROOM_KEY_PREFIX + roomId);
                // if the cache is still empty
                if (cacheItem.isEmpty()) {
                    IRoomDao roomDao = BeanManager.getLazyBeanByClass(RoomDao.class);
                    Room room = roomDao.findOneByBusinessId(roomId);
                    // if no such
                    if (room == null) {
                        throw new RequestException(
                                "room not found",
                                StatusCodeEnume.RESOURCE_NOT_FOUND_EXCEPTION.getCode());
                    }

                    // generate hotel vo
                    roomVo = new RoomVo();
                    // copy properties
                    BeanUtil.copyProperties(room, roomVo);

                    // embedded value
                    Money money = new Money();
                    money.setAmount(room.getPricePerNight());
                    money.setCurrency(room.getCurrency());
                    roomVo.setMoney(money);

                    // list amenities
                    List<RoomAmenity> amenities = roomAmenityBlo.getAllAmenitiesByRoomId(roomId);
                    roomVo.setAmenities(amenities);

                    // use random expiration time to prevent Cache Avalanche
                    cache.put(
                            CacheConstant.VO_ROOM_KEY_PREFIX + roomId,
                            roomVo,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );

                    cache.put(
                            CacheConstant.ENTITY_ROOM_KEY_PREFIX + roomId,
                            room,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    // data is put by other thread, just get from cache.
                    roomVo = (RoomVo) cacheItem.get();
                }
            }

        } else {
            roomVo = (RoomVo) cacheItem.get();
        }

        return roomVo;
    }

    @Override
    public List<RoomVo> getAllRoomsFromHotelId(String hotelId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.CACHED_ROOMS_FOR_HOTEL + hotelId);
        List<RoomVo> roomVos;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cacheItem = cache.get(CacheConstant.CACHED_ROOMS_FOR_HOTEL + hotelId);
                if (cacheItem.isEmpty()) {
                    IRoomDao roomDao = BeanManager.getLazyBeanByClass(RoomDao.class);
                    List<Room> rooms = roomDao.findRoomsByHotelId(hotelId);
                    roomVos = new LinkedList<>();
                    for (Room room : rooms) {
                        String roomId = room.getRoomId();
                        RoomVo roomVo = getRoomInfoByRoomId(roomId);
                        roomVos.add(roomVo);

                        cache.put(
                                CacheConstant.ENTITY_ROOM_KEY_PREFIX + roomId,
                                room,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    }

                    // cache result
                    cache.put(
                            CacheConstant.CACHED_ROOMS_FOR_HOTEL + hotelId,
                            roomVos,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    roomVos = (List<RoomVo>) cacheItem.get();
                }
            }
        } else {
            roomVos = (List<RoomVo>) cacheItem.get();
        }

        return roomVos;
    }
}