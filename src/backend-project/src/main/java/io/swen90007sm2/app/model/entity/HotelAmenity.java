package io.swen90007sm2.app.model.entity;

import io.swen90007sm2.app.db.annotation.Transient;

import java.util.Date;
import java.util.List;

public class HotelAmenity extends BaseAmenity {

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
