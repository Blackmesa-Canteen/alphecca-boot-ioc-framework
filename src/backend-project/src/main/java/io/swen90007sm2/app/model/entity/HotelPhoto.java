package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * hotel to photo many to many middle table
 */
public class HotelPhoto extends BaseEntity{

    private String photoId;
    private String hotelId;

    public HotelPhoto() {
    }

    public HotelPhoto(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public HotelPhoto(String photoId, String hotelId) {
        this.photoId = photoId;
        this.hotelId = hotelId;
    }

    public HotelPhoto(Date createTime, Date updateTime, String photoId, String hotelId) {
        super(createTime, updateTime);
        this.photoId = photoId;
        this.hotelId = hotelId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
