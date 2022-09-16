package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.model.entity.HotelAmenity;

import java.util.List;

public interface IHotelAmenityBlo {

    /**
     * get all hotel amenities
     */
    List<HotelAmenity> getAllAmenities();

    /**
     * get hotel amenity info by amenityId
     * @param amenityId string
     * @return hotelAmenity entity
     */
    HotelAmenity getHotelAmenityInfoByAmenityId(String amenityId);

    List<HotelAmenity> getAllAmenitiesByHotelId(String hotelId);

    void  updateAmenityIdsForHotel(List<String> amenityIds, String hotelId);
}
