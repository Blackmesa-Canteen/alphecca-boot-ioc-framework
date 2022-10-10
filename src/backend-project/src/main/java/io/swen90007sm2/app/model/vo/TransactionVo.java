package io.swen90007sm2.app.model.vo;

import io.swen90007sm2.app.model.entity.RoomOrder;
import io.swen90007sm2.app.model.entity.Transaction;
import io.swen90007sm2.app.model.pojo.Money;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 996Worker
 * @create 2022-09-05 16:02
 */
public class TransactionVo extends Transaction {

    private Money money;
    private List<RoomOrder> roomOrders;

    public TransactionVo() {
    }

    public TransactionVo(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public TransactionVo(String transactionId, String customerId, String hotelId, Integer statusCode, Date startDate, Date endDate, BigDecimal totalPrice, String currency) {
        super(transactionId, customerId, hotelId, statusCode, startDate, endDate, totalPrice, currency);
    }

    public TransactionVo(Date createTime, Date updateTime, String transactionId, String customerId, String hotelId, Integer statusCode, Date startDate, Date endDate, BigDecimal totalPrice, String currency) {
        super(createTime, updateTime, transactionId, customerId, hotelId, statusCode, startDate, endDate, totalPrice, currency);
    }

    public TransactionVo(Date createTime, Date updateTime, String transactionId, String customerId, String hotelId, Integer statusCode, Date startDate, Date endDate) {
        super(createTime, updateTime, transactionId, customerId, hotelId, statusCode, startDate, endDate);
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public List<RoomOrder> getRoomOrders() {
        return roomOrders;
    }

    public void setRoomOrders(List<RoomOrder> roomOrders) {
        this.roomOrders = roomOrders;
    }
}