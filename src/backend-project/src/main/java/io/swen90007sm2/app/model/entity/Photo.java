package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class Photo extends BaseEntity {

    private String photoId;

    private String ownerId;

    private String photoUrl;

    private String description;

    public Photo() {
    }

    public Photo(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public Photo(String photoId, String ownerId, String photoUrl, String description) {
        this.photoId = photoId;
        this.ownerId = ownerId;
        this.photoUrl = photoUrl;
        this.description = description;
    }

    public Photo(Date createTime, Date updateTime, String photoId, String ownerId, String photoUrl, String description) {
        super(createTime, updateTime);
        this.photoId = photoId;
        this.ownerId = ownerId;
        this.photoUrl = photoUrl;
        this.description = description;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    @Override
    public String toString() {
        return "Photo{" +
                "photoId='" + photoId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
