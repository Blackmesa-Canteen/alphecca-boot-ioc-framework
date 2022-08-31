package io.swen90007sm2.app.model.vo;

import io.swen90007sm2.app.model.entity.Room;
import io.swen90007sm2.app.model.entity.RoomAmenity;
import io.swen90007sm2.app.model.pojo.Money;

import java.util.List;

/**
 * @author 996Worker
 * @description roomVo
 * @create 2022-08-30 13:07
 */
public class RoomVo extends Room {

    private Money money;
    private List<RoomAmenity> amenities;

    public RoomVo() {
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public List<RoomAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<RoomAmenity> roomAmenities) {
        this.amenities = roomAmenities;
    }
}