package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class Room extends BaseEntity {

    private String roomId;

    private String name;

    private String description;

    private Double pricePerNight;

    private Integer sleepsNum;

    private Integer vacantNum;

    private Boolean onSale;

    public Room() {
    }

    public Room(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public Room(String roomId, String name, String description, Double pricePerNight, Integer sleepsNum, Integer vacantNum, Boolean onSale) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.sleepsNum = sleepsNum;
        this.vacantNum = vacantNum;
        this.onSale = onSale;
    }

    public Room(Date createTime, Date updateTime, String roomId, String name, String description, Double pricePerNight, Integer sleepsNum, Integer vacantNum, Boolean onSale) {
        super(createTime, updateTime);
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.sleepsNum = sleepsNum;
        this.vacantNum = vacantNum;
        this.onSale = onSale;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Integer getSleepsNum() {
        return sleepsNum;
    }

    public void setSleepsNum(Integer sleepsNum) {
        this.sleepsNum = sleepsNum;
    }

    public Integer getVacantNum() {
        return vacantNum;
    }

    public void setVacantNum(Integer vacantNum) {
        this.vacantNum = vacantNum;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", sleepsNum=" + sleepsNum +
                ", vacantNum=" + vacantNum +
                ", onSale=" + onSale +
                '}';
    }
}
