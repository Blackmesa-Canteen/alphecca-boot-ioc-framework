package io.swen90007sm2.app.model.entity;

import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.dao.IRoomAmenityDao;
import io.swen90007sm2.app.dao.impl.RoomAmenityDao;
import io.swen90007sm2.app.db.annotation.Transient;
import io.swen90007sm2.app.model.pojo.Money;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class Room extends BaseEntity {

    private String roomId;

    @Transient
    private List<RoomAmenity> amenities;

    private String name;

    private String description;

    // in database it is always absolute amount of AUD
    private BigDecimal pricePerNight = BigDecimal.valueOf(0.0);

    // in database, it is always AUD
    private String currency = CommonConstant.AUD_CURRENCY;

    private Integer sleepsNum = 1;

    private Integer vacantNum = 1;

    private Boolean onSale = false;

    private Money money;

    private String hotelId;

    public Room() {
    }

    public Room(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public Room(String roomId, String hotelId, String name, String description, BigDecimal pricePerNight, String currency, Integer sleepsNum, Integer vacantNum, Boolean onSale) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.currency = currency;
        this.sleepsNum = sleepsNum;
        this.vacantNum = vacantNum;
        this.onSale = onSale;
    }

    public Room(Date createTime, Date updateTime, String roomId, String hotelId, String name, String description, BigDecimal pricePerNight, String currency, Integer sleepsNum, Integer vacantNum, Boolean onSale) {
        super(createTime, updateTime);
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.currency = currency;
        this.sleepsNum = sleepsNum;
        this.vacantNum = vacantNum;
        this.onSale = onSale;
    }

    public Room(Date createTime, Date updateTime, String roomId, String hotelId, String name, String description, BigDecimal pricePerNight, Integer sleepsNum, Integer vacantNum, Boolean onSale) {
        super(createTime, updateTime);
        this.roomId = roomId;
        this.hotelId = hotelId;
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

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public List<RoomAmenity> getAmenities() {
        if (amenities == null) {
            IRoomAmenityDao amenityDao = BeanManager.getLazyBeanByClass(RoomAmenityDao.class);
            amenities = amenityDao.findAllAmenitiesByRoomId(roomId);
        }
        return amenities;
    }

    public void setAmenities(List<RoomAmenity> amenities) {
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", sleepsNum=" + sleepsNum +
                ", vacantNum=" + vacantNum +
                ", onSale=" + onSale +
                '}';
    }
}
