package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Room;
import io.swen90007sm2.app.model.param.CreateRoomParam;
import io.swen90007sm2.app.model.param.UpdateRoomParam;
import io.swen90007sm2.app.model.vo.RoomVo;

import java.util.List;

public interface IRoomBlo {

    /**
     * create a new room
     * @param param RoomParam from request
     */
    void doCreateRoomToHotel(CreateRoomParam param);

    void doUpdateRoom(UpdateRoomParam param);

    /**
     * get room entity
     */
    Room getRoomEntityByRoomId(String roomId);

    /**
     * get room vo
     */
    RoomVo getRoomInfoByRoomId(String roomId, String currencyName);

    List<RoomVo> getAllRoomsFromHotelId(String hotelId, String currencyName);
}
