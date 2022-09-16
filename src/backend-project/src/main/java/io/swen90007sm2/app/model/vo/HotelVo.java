package io.swen90007sm2.app.model.vo;

import cn.hutool.core.bean.BeanUtil;
import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.HotelAmenity;
import io.swen90007sm2.app.model.pojo.Money;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 996Worker
 * @description vo for entity hotel, contains embedded value
 * @create 2022-08-29 21:56
 */
public class HotelVo extends Hotel {

    // embedded value for currency
    private Money money;
    private List<HotelAmenity> amenities;

    public HotelVo() {
    }

    public HotelVo(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public HotelVo(String hotelId, String name, String description, String address, String postCode, BigDecimal minPrice, String currency, Integer rank, Boolean onSale) {
        super(hotelId, name, description, address, postCode, minPrice, currency, rank, onSale);
    }

    public HotelVo(Date createTime, Date updateTime, String hotelId, String name, String description, String address, String postCode, BigDecimal minPrice, String currency, Integer rank, Boolean onSale) {
        super(createTime, updateTime, hotelId, name, description, address, postCode, minPrice, currency, rank, onSale);
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public List<HotelAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<HotelAmenity> amenities) {
        this.amenities = amenities;
    }
}