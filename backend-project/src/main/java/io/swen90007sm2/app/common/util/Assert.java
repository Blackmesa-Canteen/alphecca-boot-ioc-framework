package io.swen90007sm2.app.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 996Worker
 * @description helper assert class
 * @create 2022-08-03 21:40
 */
public class Assert {

    private static final Logger LOGGER = LoggerFactory.getLogger(Assert.class);

    public static void notNull (Object obj, String msg) {
        if (obj == null) {
            LOGGER.error("Assertion not null: {}", msg);
            System.exit(-1);
        }
    }

    public static void isTrue(boolean isTrue, String msg) {
        if(!isTrue) {
            LOGGER.error("Assertion is true: {}", msg);
            System.exit(-1);
        }
    }

    public static void hasText(Object obj, String msg) {
        if (!(obj instanceof String && StringUtils.isNotEmpty((String) obj))) {
            LOGGER.error("Assertion has text: {}", msg);
            System.exit(-1);
        }
    }
}