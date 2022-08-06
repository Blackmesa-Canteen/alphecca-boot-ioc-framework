package io.swen90007sm2.app.common.util;

import io.swen90007sm2.alpheccaboot.exception.InternalException;
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
            LOGGER.error("Assertion must not null: {}", msg);
            throw new InternalException("Assertion failure.");
        }
    }

    public static void isTrue(boolean isTrue, String msg) {
        if(!isTrue) {
            LOGGER.error("Assertion must be true: {}", msg);
            throw new InternalException("Assertion failure.");
        }
    }

    public static void hasText(Object obj, String msg) {
        if (!(obj instanceof String && StringUtils.isNotEmpty((String) obj))) {
            LOGGER.error("Assertion must have text: {}", msg);
            throw new InternalException("Assertion failure.");
        }
    }
}