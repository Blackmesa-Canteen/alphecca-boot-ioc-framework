package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.param.RoomParam;

public interface IRoomBlo {

    /**
     * create a new room
     * @param hotelId target hotelId
     * @param param RoomParam from request
     */
    void doCreateRoom(String hotelId, RoomParam param);
}
