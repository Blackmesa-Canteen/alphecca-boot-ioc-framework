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

    void doUpdateRoomWithLock(UpdateRoomParam param, String userId);

    void doDeleteRoomByRoomId(String RoomId);

    /**
     * get room entity
     */
    Room getRoomEntityByRoomId(String roomId);

    Room getRoomEntityByRoomIdWithLock(String roomId, String userId);

    /**
     * get room vo
     * @param showNotSale show not sale result?
     */
    RoomVo getRoomInfoByRoomId(String roomId, String currencyName, Boolean showNotSale);

    List<RoomVo> getAllRoomsFromHotelId(String hotelId, String currencyName, Boolean showNotSale);

    List<Room> getAllRoomEntitiesFromHotelId(String hotelId);

    /**
     * call to get hotlier's owned hotel's all rooms
     * @param hotelierUserId
     * @return
     */
    List<RoomVo> getOwnedHotelRoomVos(String hotelierUserId);
}
