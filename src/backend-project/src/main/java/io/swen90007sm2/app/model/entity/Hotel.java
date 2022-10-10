package io.swen90007sm2.app.model.entity;

import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.dao.IRoomDao;
import io.swen90007sm2.app.dao.impl.HotelAmenityDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
import io.swen90007sm2.app.dao.impl.RoomDao;
import io.swen90007sm2.app.model.pojo.Money;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Hotel extends BaseEntity{
    private String hotelId;
    private String name;
    private String description;
    private String address;
    private String postCode;

    // this value is AUD absolute price
    private BigDecimal minPrice = BigDecimal.valueOf(0.0);

    // this always be AUD in the database
    private String currency = "AUD";

    private Integer rank = 3;
    private Boolean onSale = false;

    private Money money;
    private List<HotelAmenity> amenities;

    private List<Room> rooms;

    public Hotel() {
    }

    public Hotel(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }


    public Hotel(String hotelId, String name, String description, String address, String postCode, BigDecimal minPrice, String currency, Integer rank, Boolean onSale) {
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.postCode = postCode;
        this.minPrice = minPrice;
        this.currency = currency;
        this.rank = rank;
        this.onSale = onSale;
    }

    public Hotel(Date createTime, Date updateTime, String hotelId, String name, String description, String address, String postCode, BigDecimal minPrice, String currency, Integer rank, Boolean onSale) {
        super(createTime, updateTime);
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.postCode = postCode;
        this.minPrice = minPrice;
        this.currency = currency;
        this.rank = rank;
        this.onSale = onSale;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
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

    public void setAmenities(List<HotelAmenity> amenities) {
        this.amenities = amenities;
    }

    public List<Room> getRooms() {
        if (rooms == null) {
            IRoomDao mapper = BeanManager.getLazyBeanByClass(RoomDao.class);
            rooms = mapper.findRoomsByHotelId(hotelId);
        }

        return rooms;
    }

    public List<HotelAmenity> getAmenities() {
        if (amenities == null) {
            IHotelAmenityDao mapper = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
            amenities = mapper.findAllAmenitiesByHotelId(hotelId);
        }

        return amenities;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId='" + hotelId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", minPrice=" + minPrice +
                ", rank=" + rank +
                ", onSale=" + onSale +
                '}';
    }
}
