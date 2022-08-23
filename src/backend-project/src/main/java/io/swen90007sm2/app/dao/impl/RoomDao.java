package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IRoomDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 996Worker
 * @description room dao
 * @create 2022-08-23 11:39
 */
@Dao
@Lazy
public class RoomDao implements IRoomDao {

    @Override
    public int insertOne(Room entity) {
        return RoomMapper.getInstance().insertOne(entity);
    }

    @Override
    public int updateOne(Room entity) {
        return RoomMapper.getInstance().updateOne(entity);
    }

    @Override
    public int deleteOne(Room entity) {
        return RoomMapper.getInstance().deleteOne(entity);
    }

    @Override
    public Room findOneByBusinessId(String roomId) {
        return RoomMapper.getInstance().findOneByBusinessId(roomId);
    }

    @Override
    public int findTotalCountByHotelId(String hotelId) {
        return RoomMapper.getInstance().findTotalCountByHotelId(hotelId);
    }

    @Override
    public List<Room> findRoomsByHotelIdByPage(String hotelId, Integer start, Integer rows) {
        return RoomMapper.getInstance().findRoomsByHotelIdByPage(hotelId, start, rows);
    }

    /**
     * helper class for lazy loading
     * <br/>
     * static class is in jvm meta space as static codes, need to be instantiated and load into JVM heap
     * to use, the same as other classes.
     */
    private static class RoomMapper {
        private static final Logger LOGGER = LoggerFactory.getLogger(RoomMapper.class);
        // Lazy loaded singleton
        private static volatile RoomMapper instance = null;

        private static RoomMapper getInstance() {

            // check existence without synchronized lock
            if (instance == null) {
                synchronized (RoomMapper.class) {
                    // double check to prevent duplicate instance
                    if (instance == null) {
                        instance = new RoomMapper();
                        LOGGER.info("Lazy loaded: [{}]", instance.getClass().getName());
                    }
                }
            }

            return instance;
        }

        public int insertOne(Room entity) {
            return CRUDTemplate.executeNonQuery(
                    "INSERT INTO room (id, room_id, name, description, price_per_night, " +
                            "sleeps_num, vacant_num, on_sale, hotel_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    entity.getId(),
                    entity.getRoomId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getPricePerNight(),
                    entity.getSleepsNum(),
                    entity.getVacantNum(),
                    entity.getOnSale(),
                    entity.getHotelId()
            );
        }

        public int updateOne(Room entity) {
            return CRUDTemplate.executeNonQuery(
                    "UPDATE room SET name = ?, description = ?, price_per_night = ?, " +
                            "sleeps_num = ?, vacant_num = ?, on_sale = ?, update_time = ? WHERE id = ?",
                    entity.getName(),
                    entity.getDescription(),
                    entity.getPricePerNight(),
                    entity.getSleepsNum(),
                    entity.getVacantNum(),
                    entity.getOnSale(),
                    new java.sql.Date(TimeUtil.now().getTime()),
                    entity.getId()
            );
        }

        public int deleteOne(Room entity) {
            return CRUDTemplate.executeNonQuery(
                    "DELETE FROM room WHERE id = ?",
                    entity.getId()
            );
        }

        public Room findOneByBusinessId(String roomId) {
            return CRUDTemplate.executeQueryWithOneRes(
                    Room.class,
                    "SELECT * FROM room WHERE room_id = ?",
                    roomId
            );
        }

        public int findTotalCountByHotelId(String hotelId) {
            Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                    Long.class,
                    "SELECT count(*) FROM room WHERE hotel_id = ?",
                    hotelId
            );

            return (totalRows != null) ? totalRows.intValue() : 0;
        }

        public List<Room> findRoomsByHotelIdByPage(String hotelId, Integer start, Integer rows) {
            List<Room> rooms = CRUDTemplate.executeQueryWithMultiRes(
                    Room.class,
                    "SELECT * FROM room WHERE hotel_id = ? OFFSET ? LIMIT ?",
                    hotelId,
                    start,
                    rows
            );
            return rooms;
        }


    }
}