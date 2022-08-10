package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * room to room_amenity many to many middle table
 */
public class RoomRoomAmenity extends BaseEntity {

    private String hotelId;
    private String amenityId;

    public RoomRoomAmenity() {
    }

    public RoomRoomAmenity(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public RoomRoomAmenity(String hotelId, String amenityId) {
        this.hotelId = hotelId;
        this.amenityId = amenityId;
    }

    public RoomRoomAmenity(Date createTime, Date updateTime, String hotelId, String amenityId) {
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
