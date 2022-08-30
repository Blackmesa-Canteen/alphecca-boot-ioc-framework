package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.param.RoomParam;
import io.swen90007sm2.app.model.vo.RoomVo;

import java.util.List;

public interface IRoomBlo {

    /**
     * create a new room
     * @param param RoomParam from request
     */
    void doCreateRoomToHotel(RoomParam param);

    void updateRoomByRoomId(RoomParam param);

    RoomVo getRoomInfoByRoomId(String roomId);

    List<RoomVo> getAllRoomsFromHotelId(String hotelId);
}
