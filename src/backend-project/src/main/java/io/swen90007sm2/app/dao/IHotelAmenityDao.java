package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.HotelAmenity;

import java.util.List;

public interface IHotelAmenityDao extends IBaseDao<HotelAmenity>{

    /**
     * find all hotel amenities
     */
    List<HotelAmenity> findAllAmenities();

    /**
     * find the hotel amenity by amentity_id
     */
    HotelAmenity findOneAmenityByAmenityId(String amenityId);


    /**
     * Association query for all amenities belongs to one hotel
     * @param hotelId hotelId
     * @return List<HotelAmenity>
     */
    List<HotelAmenity> findAllAmenitiesByHotelId(String hotelId);

    /**
     * add amenity IDs to a hotel.
     * @param amenityId
     * @param hotelId
     */
    void  addAmenityIdsToHotel(List<String> amenityId, String hotelId);

    /**
     * clear all amenities for a hotel
     */
    int clearAmenityIdsForHotel(String hotelId);
}

