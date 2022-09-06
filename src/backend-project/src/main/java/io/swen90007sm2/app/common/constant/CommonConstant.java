package io.swen90007sm2.app.common.constant;

import java.math.BigDecimal;

public interface CommonConstant {

    String NULL = "null";

    Double DEFAULT_PRICE_DOUBLE = 0.0;
    BigDecimal DEFAULT_PRICE_BIG_DECIMAL = BigDecimal.valueOf(0.0);
    Integer SORT_UP = 1;
    Integer SORT_DOWN = 0;


    String AUD_CURRENCY = "AUD";
    String USD_CURRENCY = "USD";
    String RMB_CURRENCY = "RMB";

    /* Note: for simplicity, transaction only have 2 states: confirmed and cancelled*/
    Integer TRANSACTION_PENDING = 0;
    Integer TRANSACTION_CONFIRMED = 1;
    Integer TRANSACTION_FINISHED = 2;
    Integer TRANSACTION_CANCELLED = -1;

    Integer SORT_BY_CREATE_TIME = 0;
    Integer SORT_BY_PRICE = 1;
    Integer SORT_BY_RANK = 2;

}
