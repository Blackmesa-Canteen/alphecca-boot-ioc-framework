package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.RoomAmenity;

import java.util.List;

public interface IRoomAmenityBlo {

    List<RoomAmenity> getAllAmenities();

    RoomAmenity getRoomAmenityInfoByAmenityId(String amenityId);

    List<RoomAmenity> getAllAmenitiesByRoomId(String roomId);

    void updateAmenityIdsForRoom(List<String> amenityIds, String roomId);
}
