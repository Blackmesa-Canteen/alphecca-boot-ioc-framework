package io.swen90007sm2.app.model.param;

import javax.validation.constraints.NotNull;

public class CustomerUpdateRoomOrderParam {
    @NotNull(message = "transactionId should not be null")
    String transactionId;
    @NotNull(message = "roomOrderId should not be null")
    String roomOrderId;
    @NotNull(message = "room quantity should not be null")
    int newQuantity;

    public CustomerUpdateRoomOrderParam() {
    }

    public CustomerUpdateRoomOrderParam(String transactionId, String roomOrderId, int newQuantity) {
        this.transactionId = transactionId;
        this.roomOrderId = roomOrderId;
        this.newQuantity = newQuantity;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRoomOrderId() {
        return roomOrderId;
    }

    public void setRoomOrderId(String roomOrderId) {
        this.roomOrderId = roomOrderId;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
}
