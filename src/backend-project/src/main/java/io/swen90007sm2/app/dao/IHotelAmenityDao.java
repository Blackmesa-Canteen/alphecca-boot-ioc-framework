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
}
