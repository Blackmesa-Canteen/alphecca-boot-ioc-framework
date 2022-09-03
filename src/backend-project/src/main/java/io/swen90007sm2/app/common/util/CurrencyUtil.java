package io.swen90007sm2.app.common.util;

import io.swen90007sm2.app.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author 996Worker
 * @description util for currency
 * @create 2022-09-03 12:39
 */
public class CurrencyUtil {


    /**
     * helper to get exchange rate, it should check online, but for easy, just hard code it.
     *
     * use AUD as baseline
     */
    public static Double getExchangeRateForCurrency(String currencyName) {
        if (StringUtils.isEmpty(currencyName)) return 1.0;

        switch (currencyName) {
            case CommonConstant.USD_CURRENCY:
                return 0.68;
            case CommonConstant.RMB_CURRENCY:
                return 4.7;
            default:
                return 1.0;
        }
    }

    public static BigDecimal convertCurrencyToAUD(String originalCurrencyName, BigDecimal originalAmount) {

        double originalExchangeRate = getExchangeRateForCurrency(originalCurrencyName);
        double audAmount = originalAmount.doubleValue() * (1.0 / originalExchangeRate);
        return BigDecimal.valueOf(audAmount);
    }

    public static BigDecimal convertAUDtoCurrency(String targetCurrencyName, BigDecimal audAmount) {
        double targetExchangeRate = getExchangeRateForCurrency(targetCurrencyName);
        double targetAmount = audAmount.doubleValue() * targetExchangeRate;

        return BigDecimal.valueOf(targetAmount);
    }

    public static BigDecimal convertCurrencyFromTo(BigDecimal originalAmount, String originalCurrencyName, String targetCurrencyName) {
        BigDecimal audMoney = convertCurrencyToAUD(originalCurrencyName, originalAmount);
        return convertAUDtoCurrency(targetCurrencyName, audMoney);
    }
}