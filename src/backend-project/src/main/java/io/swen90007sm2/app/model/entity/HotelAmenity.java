package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class HotelAmenity extends Amenity {
    public HotelAmenity() {
    }

    public HotelAmenity(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public HotelAmenity(String amenityId, String iconUrl, String description) {
        super(amenityId, iconUrl, description);
    }

    public HotelAmenity(Date createTime, Date updateTime, String amenityId, String iconUrl, String description) {
        super(createTime, updateTime, amenityId, iconUrl, description);
    }

}
