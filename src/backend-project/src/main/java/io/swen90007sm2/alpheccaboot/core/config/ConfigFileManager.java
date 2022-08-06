package io.swen90007sm2.alpheccaboot.core.config;

import io.swen90007sm2.alpheccaboot.common.constant.ConfigFileConstant;
import io.swen90007sm2.alpheccaboot.common.util.ConfigFileUtil;

import java.util.Properties;

/**
 * a helper to get configs from the file
 *
 * @author xiaotian
 */
public class ConfigFileManager {
    
    /*
     * props read from the prop config file
     */
    private static final Properties CONFIG_FILE_PROPS = ConfigFileUtil.loadProps(ConfigFileConstant.CONFIG_FILE_NAME);
    
    /*
     * jdbc
     */
    public static String getDbDriver() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.DB_DRIVER);
    }

    public static String getDbIp() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.DB_IP);
    }

    public static String getDbUsername() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.DB_USERNAME);
    }

    public static String getDbPassword() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.DB_PASSWORD);
    }

    public static String getDbSchemeName() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.DB_SCHEME_NAME);
    }

    public static String getServerHostName() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.APP_SERVER_HOSTNAME);
    }

    public static String getServerPortNumber() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.APP_SERVER_PORT);
    }

    /**
     * get application package name
     */
    public static String getBasePackageName() {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, ConfigFileConstant.APP_BASE_PACKAGE_NAME);
    }

    /**
     * get customized k-v prop
     */
    public static String getString(String key) {
        return ConfigFileUtil.getString(CONFIG_FILE_PROPS, key);
    }

    public static int getInt(String key) {
        return ConfigFileUtil.getInt(CONFIG_FILE_PROPS, key);
    }

    public static boolean getBoolean(String key) {
        return ConfigFileUtil.getBoolean(CONFIG_FILE_PROPS, key);
    }
}
