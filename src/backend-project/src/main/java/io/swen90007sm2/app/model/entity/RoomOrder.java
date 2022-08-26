package io.swen90007sm2.app.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RoomOrder extends BaseEntity{

    private String roomOrderId;

    private String transactionId;

    private String roomId;

    private Integer orderedCount;

    private BigDecimal pricePerRoom;

    private String currency;

    public RoomOrder() {
    }

    public RoomOrder(String roomOrderId, String transactionId, String roomId, Integer orderedCount, BigDecimal pricePerRoom, String currency) {
        this.roomOrderId = roomOrderId;
        this.transactionId = transactionId;
        this.roomId = roomId;
        this.orderedCount = orderedCount;
        this.pricePerRoom = pricePerRoom;
        this.currency = currency;
    }

    public RoomOrder(Date createTime, Date updateTime, String roomOrderId, String transactionId, String roomId, Integer orderedCount, BigDecimal pricePerRoom, String currency) {
        super(createTime, updateTime);
        this.roomOrderId = roomOrderId;
        this.transactionId = transactionId;
        this.roomId = roomId;
        this.orderedCount = orderedCount;
        this.pricePerRoom = pricePerRoom;
        this.currency = currency;
    }

    public RoomOrder(Date createTime, Date updateTime, String roomOrderId, String transactionId, String roomId, Integer orderedCount, BigDecimal pricePerRoom) {
        super(createTime, updateTime);
        this.roomOrderId = roomOrderId;
        this.transactionId = transactionId;
        this.roomId = roomId;
        this.orderedCount = orderedCount;
        this.pricePerRoom = pricePerRoom;
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
