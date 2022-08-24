package io.swen90007sm2.app.model.pojo;

import java.math.BigDecimal;

/**
 * @author 996Worker
 * @description money pojo
 * @create 2022-08-24 23:13
 */
public class Money {

    private BigDecimal amount;
    private String currency;

    public Money() {
    }

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}