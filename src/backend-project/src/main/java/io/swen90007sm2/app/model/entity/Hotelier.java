package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class Hotelier extends BaseUser {
    String hotelId;

    public Hotelier() {
    }

    public Hotelier(String hotelId) {
        this.hotelId = hotelId;
    }

    public Hotelier(String userId, String name, String description, String password, String avatarUrl, String hotelId) {
        super(userId, name, description, password, avatarUrl);
        this.hotelId = hotelId;
    }

    public Hotelier(Date createTime, Date updateTime, String userId, String name, String description, String password, String avatarUrl, String hotelId) {
        super(createTime, updateTime, userId, name, description, password, avatarUrl);
        this.hotelId = hotelId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "Hotelier{" +
                "hotelId='" + hotelId + '\'' +
                '}';
    }
}
