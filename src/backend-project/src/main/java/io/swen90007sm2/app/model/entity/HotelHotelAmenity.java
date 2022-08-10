package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * many to many middle table
 */
public class HotelHotelAmenity extends BaseEntity {

    private String hotelId;
    private String amenityId;

    public HotelHotelAmenity() {
    }

    public HotelHotelAmenity(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public HotelHotelAmenity(String hotelId, String amenityId) {
        this.hotelId = hotelId;
        this.amenityId = amenityId;
    }

    public HotelHotelAmenity(Date createTime, Date updateTime, String hotelId, String amenityId) {
        super(createTime, updateTime);
        this.hotelId = hotelId;
        this.amenityId = amenityId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(String amenityId) {
        this.amenityId = amenityId;
    }
}
