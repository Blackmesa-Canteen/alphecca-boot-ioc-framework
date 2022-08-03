package io.swen90007sm2.alpheccaboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * util class for reading .properties file
 *
 * application.properties should locate in your application's resources dir.
 *
 * @author xiaotian
 * @author tyshawnlee https://github.com/tyshawnlee/handwritten-mvc
 */

public final class ConfigFileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileUtil.class);

    /**
     * Load prop file
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream inputStream = null;
        try {
            inputStream = ClassLoadUtil.getContextClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException(fileName + "property file is not found");
            }
            props = new Properties();
            props.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    /**
     * get property String value, default is ""
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * get property value with a default value if empty
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * get property boolean value, default is false
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = Boolean.parseBoolean(props.getProperty(key));
        }
        return value;
    }

    /**
     * get property int value, default is 0
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * get property int value with a default value if empty
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = Integer.parseInt(props.getProperty(key));
        }
        return value;
    }
}
