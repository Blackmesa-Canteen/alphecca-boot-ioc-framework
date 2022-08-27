package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.model.entity.HotelAmenity;

import java.util.List;

public interface IHotelAmenityBlo {

    List<HotelAmenity> getAllAmenities();

    HotelAmenity getHotelAmenityInfoByAmenityId(String amenityId);
}
