package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.Room;

import java.util.List;

public interface IRoomDao extends IBaseDao<Room>{

    /**
     * Find room by roomId
     * @param roomId is business specific Id, not database id
     * @return Customer Entity
     */
    Room findOneByBusinessId(String roomId);

    /**
     * find record count of rooms that belongs to a hotel
     * @param hotelId owner hotel ID
     * @return record count
     */
    int findTotalCountByHotelId(String hotelId);

    /**
     * find all rooms of a hotel, and query by page
     * @param hotelId owner hotel id
     * @param start start offset
     * @param rows result rows
     * @return
     */
    List<Room> findRoomsByHotelIdByPage(String hotelId, Integer start, Integer rows);
}
