package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class Photo extends BaseEntity {

    private String photoId;
    private String photoUrl;

    // the photo resource belongs to user
    private String userId;
    private String description;

    public Photo() {
    }

    public Photo(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public Photo(String photoId, String photoUrl, String userId, String description) {
        this.photoId = photoId;
        this.photoUrl = photoUrl;
        this.userId = userId;
        this.description = description;
    }

    public Photo(Date createTime, Date updateTime, String photoId, String photoUrl, String userId, String description) {
        super(createTime, updateTime);
        this.photoId = photoId;
        this.photoUrl = photoUrl;
        this.userId = userId;
        this.description = description;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId='" + photoId + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
