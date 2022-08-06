package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-06 23:37
 */
public abstract class Amenity extends BaseEntity {

    private String amenityId;

    private String iconUrl;

    private String description;

    public Amenity() {
    }

    public Amenity(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public Amenity(String amenityId, String iconUrl, String description) {
        this.amenityId = amenityId;
        this.iconUrl = iconUrl;
        this.description = description;
    }

    public Amenity(Date createTime, Date updateTime, String amenityId, String iconUrl, String description) {
        super(createTime, updateTime);
        this.amenityId = amenityId;
        this.iconUrl = iconUrl;
        this.description = description;
    }

    public String getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(String amenityId) {
        this.amenityId = amenityId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Amenity{" +
                "amenityId='" + amenityId + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}