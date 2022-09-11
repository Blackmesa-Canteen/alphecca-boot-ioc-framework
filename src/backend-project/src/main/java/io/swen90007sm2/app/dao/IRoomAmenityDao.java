package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.RoomAmenity;

import java.util.List;

public interface IRoomAmenityDao extends IBaseDao<RoomAmenity>{

    /**
     * find all
     */
    List<RoomAmenity> findAllAmenities();

    /**
     * find entity by business id
     */
    RoomAmenity findAmenityBeAmenityId(String amenityId);

    /**
     * associate query to get all amenities for a room
     */
    List<RoomAmenity> findAllAmenitiesByRoomId(String roomId);

    /**
     * batched insertion of associate table, add amenities to a room
     */
    void addAmenityIdsToRoom(List<String> amenityIds, String roomId);

    /**
     * clear all amenities of a room in associate table
     */
    int clearAmenityIdsForRoom(String roomId);
}
