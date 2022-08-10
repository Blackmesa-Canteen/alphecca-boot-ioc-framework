package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * room to photo many to many middle table
 */
public class RoomPhoto extends BaseEntity {

    private String roomId;
    private String photoId;

    public RoomPhoto() {
    }

    public RoomPhoto(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public RoomPhoto(String roomId, String photoId) {
        this.roomId = roomId;
        this.photoId = photoId;
    }

    public RoomPhoto(Date createTime, Date updateTime, String roomId, String photoId) {
        super(createTime, updateTime);
        this.roomId = roomId;
        this.photoId = photoId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
}
