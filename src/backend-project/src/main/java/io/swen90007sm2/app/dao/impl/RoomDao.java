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
        return CRUDTemplate.executeNonQuery(
                "INSERT INTO room (id, room_id, name, description, price_per_night, " +
                        "sleeps_num, vacant_num, on_sale, hotel_id, currency) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getRoomId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPricePerNight(),
                entity.getSleepsNum(),
                entity.getVacantNum(),
                entity.getOnSale(),
                entity.getHotelId(),
                entity.getCurrency()
        );
    }

    @Override
    public int updateOne(Room entity) {
        return CRUDTemplate.executeNonQuery(
                "UPDATE room SET name = ?, description = ?, price_per_night = ?, " +
                        "sleeps_num = ?, vacant_num = ?, on_sale = ?, currency = ?, update_time = ? WHERE id = ?",
                entity.getName(),
                entity.getDescription(),
                entity.getPricePerNight(),
                entity.getSleepsNum(),
                entity.getVacantNum(),
                entity.getOnSale(),
                entity.getCurrency(),
                new java.sql.Date(TimeUtil.now().getTime()),
                entity.getId()
        );
    }

    @Override
    public int deleteOne(Room entity) {
        return CRUDTemplate.executeNonQuery(
                "DELETE FROM room WHERE id = ?",
                entity.getId()
        );
    }

    @Override
    public Room findOneByBusinessId(String roomId) {
        return CRUDTemplate.executeQueryWithOneRes(
                Room.class,
                "SELECT * FROM room WHERE room_id = ?",
                roomId
        );
    }

    @Override
    public int findTotalCountByHotelId(String hotelId) {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM room WHERE hotel_id = ?",
                hotelId
        );

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
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