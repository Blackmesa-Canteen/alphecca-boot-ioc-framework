package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelBlo;
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
import io.swen90007sm2.app.model.param.RoomParam;
import io.swen90007sm2.app.model.vo.HotelVo;
import io.swen90007sm2.app.model.vo.RoomVo;

import java.util.List;

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

    @Override
    public void doCreateRoomToHotel(RoomParam param) {
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
    public void updateRoomByRoomId(RoomParam param) {
        throw new NotImplementedException();
    }

    @Override
    public RoomVo getRoomInfoByRoomId(String roomId) {
        throw new NotImplementedException();
    }

    @Override
    public List<RoomVo> getAllRoomsFromHotelId(String hotelId) {
        throw new NotImplementedException();
    }
}