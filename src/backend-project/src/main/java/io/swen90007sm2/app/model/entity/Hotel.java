package io.swen90007sm2.app.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Hotel extends BaseEntity{
    private String hotelId;
    private String name;
    private String description;
    private String address;
    private String postCode;
    private Double minPrice = 0.0;

    private String currency = "AUD";

    private Integer rank = 3;
    private Boolean onSale = false;

    public Hotel() {
    }

    public Hotel(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }


    public Hotel(String hotelId, String name, String description, String address, String postCode, Double minPrice, String currency, Integer rank, Boolean onSale) {
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

    public Hotel(Date createTime, Date updateTime, String hotelId, String name, String description, String address, String postCode, Double minPrice, String currency, Integer rank, Boolean onSale) {
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
