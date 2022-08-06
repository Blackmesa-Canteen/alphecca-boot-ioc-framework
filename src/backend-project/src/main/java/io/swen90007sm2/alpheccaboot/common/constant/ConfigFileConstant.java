package io.swen90007sm2.alpheccaboot.common.constant;


/**
 * constants that are used for configuration file
 *
 * @author xiaotian
 */
public interface ConfigFileConstant {
    
    String CONFIG_FILE_NAME = "application.properties";

    // filepath
    String APP_BASE_PACKAGE_NAME = "io.swen90007sm2.app.base_package";

    // server
    String APP_SERVER_HOSTNAME = "io.swen90007sm2.app.server.hostname";
    String APP_SERVER_PORT = "io.swen90007sm2.app.server.port";

    // database
    String DB_DRIVER = "io.swen90007sm2.db.driver";
    String DB_IP = "io.swen90007sm2.db.ip";
    String DB_USERNAME = "io.swen90007sm2.db.username";
    String DB_PASSWORD = "io.swen90007sm2.db.password";
    String DB_DB_NAME = "io.swen90007sm2.db.dbName";
    String DB_SCHEME_NAME = "io.swen90007sm2.db.schemeName";
}
