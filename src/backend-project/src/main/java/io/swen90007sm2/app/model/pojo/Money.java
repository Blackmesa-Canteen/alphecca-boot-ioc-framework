package io.swen90007sm2.app.model.pojo;

import io.swen90007sm2.app.common.util.CurrencyUtil;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author 996Worker
 * @description money pojo
 * @create 2022-08-24 23:13
 */
public class Money implements Comparable<Money> {

    private BigDecimal amount;
    private String currency;

    @Override
    public int compareTo(Money other) {
        BigDecimal absoluteAmount = CurrencyUtil.convertCurrencyToAUD(this.currency, this.amount);
        BigDecimal otherAbsoluteAmount = CurrencyUtil.convertCurrencyToAUD(other.currency, other.amount);

        return absoluteAmount.compareTo(otherAbsoluteAmount);
    }

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