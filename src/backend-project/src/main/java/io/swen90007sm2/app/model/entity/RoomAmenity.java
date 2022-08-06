package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class RoomAmenity extends BaseAmenity {

    public RoomAmenity() {
    }

    public RoomAmenity(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public RoomAmenity(String amenityId, String iconUrl, String description) {
        super(amenityId, iconUrl, description);
    }

    public RoomAmenity(Date createTime, Date updateTime, String amenityId, String iconUrl, String description) {
        super(createTime, updateTime, amenityId, iconUrl, description);
    }
}
