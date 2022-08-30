package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.param.RoomParam;
import io.swen90007sm2.app.model.vo.RoomVo;

import java.util.List;

public interface IRoomBlo {

    /**
     * create a new room
     * @param hotelId target hotelId
     * @param param RoomParam from request
     */
    void doCreateRoomToHotel(String hotelId, RoomParam param);

    void updateRoomByRoomId(String roomId, RoomParam param);

    RoomVo getRoomInfoByRoomId(String roomId);

    List<RoomVo> getAllRoomsFromHotelId(String hotelId);
}
