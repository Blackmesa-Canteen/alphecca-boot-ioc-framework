package io.swen90007sm2.app.model.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 996Worker
 * @description transaction entity
 * @create 2022-08-06 23:45
 */
public class Transaction extends BaseEntity {

    private String transactionId;

    private String customerId;

    private String hotelId;

    private Integer statusCode;

    private Date startDate;

    private Date endDate;

    private BigDecimal totalPrice;

    private String currency;

    public Transaction() {
    }

    public Transaction(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public Transaction(String transactionId, String customerId, String hotelId, Integer statusCode, Date startDate, Date endDate, BigDecimal totalPrice, String currency) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.hotelId = hotelId;
        this.statusCode = statusCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public Transaction(Date createTime, Date updateTime, String transactionId, String customerId, String hotelId, Integer statusCode, Date startDate, Date endDate, BigDecimal totalPrice, String currency) {
        super(createTime, updateTime);
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.hotelId = hotelId;
        this.statusCode = statusCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public Transaction(Date createTime, Date updateTime, String transactionId, String customerId, String hotelId, Integer statusCode, Date startDate, Date endDate) {
        super(createTime, updateTime);
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.hotelId = hotelId;
        this.statusCode = statusCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", statusCode=" + statusCode +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}