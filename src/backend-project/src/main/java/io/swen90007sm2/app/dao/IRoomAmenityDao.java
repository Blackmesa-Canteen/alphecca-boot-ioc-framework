package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.RoomAmenity;

import java.util.List;

public interface IRoomAmenityDao extends IBaseDao<RoomAmenity>{

    List<RoomAmenity> findAllAmenities();

    RoomAmenity findAmenityBeAmenityId(String amenityId);

    List<RoomAmenity> findAllAmenitiesByRoomId(String roomId);

    void addAmenityIdsToRoom(List<String> amenityIds, String roomId);

    int clearAmenityIdsForRoom(String roomId);
}
