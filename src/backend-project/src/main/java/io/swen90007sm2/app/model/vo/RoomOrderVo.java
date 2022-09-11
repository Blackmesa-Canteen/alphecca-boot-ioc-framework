package io.swen90007sm2.app.model.vo;

import io.swen90007sm2.app.model.entity.RoomOrder;
import io.swen90007sm2.app.model.pojo.Money;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-05 16:08
 */
public class RoomOrderVo extends RoomOrder {

    private Money money;

    public RoomOrderVo(Money money) {
        this.money = money;
    }

    public RoomOrderVo(String roomOrderId, String transactionId, String roomId, String hotelId, String customerId, Integer orderedCount, BigDecimal pricePerRoom, String currency, Money money) {
        super(roomOrderId, transactionId, roomId, hotelId, customerId, orderedCount, pricePerRoom, currency);
        this.money = money;
    }

    public RoomOrderVo(Date createTime, Date updateTime, String roomOrderId, String transactionId, String roomId, String hotelId, String customerId, Integer orderedCount, BigDecimal pricePerRoom, String currency, Money money) {
        super(createTime, updateTime, roomOrderId, transactionId, roomId, hotelId, customerId, orderedCount, pricePerRoom, currency);
        this.money = money;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }
}