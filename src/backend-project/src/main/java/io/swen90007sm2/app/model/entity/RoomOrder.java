package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class RoomOrder extends BaseEntity{

    private String roomOrderId;

    private String transactionId;

    private String roomId;

    private Date startDate;

    private Date endDate;

    public RoomOrder() {
    }

    public RoomOrder(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public RoomOrder(String roomOrderId, String transactionId, String roomId, Date startDate, Date endDate) {
        this.roomOrderId = roomOrderId;
        this.transactionId = transactionId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RoomOrder(Date createTime, Date updateTime, String roomOrderId, String transactionId, String roomId, Date startDate, Date endDate) {
        super(createTime, updateTime);
        this.roomOrderId = roomOrderId;
        this.transactionId = transactionId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "RoomOrder{" +
                "roomOrderId='" + roomOrderId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
