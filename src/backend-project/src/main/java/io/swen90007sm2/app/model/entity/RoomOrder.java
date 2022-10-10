package io.swen90007sm2.app.model.entity;

import io.swen90007sm2.app.model.pojo.Money;

import java.math.BigDecimal;
import java.util.Date;

public class RoomOrder extends BaseEntity{

    private String roomOrderId;

    private String transactionId;

    private String roomId;

    private String hotelId;

    private String customerId;

    private Integer orderedCount;

    // in database it is always absolute amount of AUD
    private BigDecimal pricePerRoom;

    // in database it is always AUD
    private String currency;

    private Money money;

    public RoomOrder() {
    }

    public RoomOrder(String roomOrderId, String transactionId, String roomId, String hotelId, String customerId, Integer orderedCount, BigDecimal pricePerRoom, String currency) {
        this.roomOrderId = roomOrderId;
        this.transactionId = transactionId;
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.customerId = customerId;
        this.orderedCount = orderedCount;
        this.pricePerRoom = pricePerRoom;
        this.currency = currency;
    }

    public RoomOrder(Date createTime, Date updateTime, String roomOrderId, String transactionId, String roomId, String hotelId, String customerId, Integer orderedCount, BigDecimal pricePerRoom, String currency) {
        super(createTime, updateTime);
        this.roomOrderId = roomOrderId;
        this.transactionId = transactionId;
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.customerId = customerId;
        this.orderedCount = orderedCount;
        this.pricePerRoom = pricePerRoom;
        this.currency = currency;
    }

    public String getRoomOrderId() {
        return roomOrderId;
    }

    public void setRoomOrderId(String roomOrderId) {
        this.roomOrderId = roomOrderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(Integer orderedCount) {
        this.orderedCount = orderedCount;
    }

    public BigDecimal getPricePerRoom() {
        return pricePerRoom;
    }

    public void setPricePerRoom(BigDecimal pricePerRoom) {
        this.pricePerRoom = pricePerRoom;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "RoomOrder{" +
                "roomOrderId='" + roomOrderId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", orderedCount=" + orderedCount +
                ", pricePerRoom=" + pricePerRoom +
                '}';
    }
}
