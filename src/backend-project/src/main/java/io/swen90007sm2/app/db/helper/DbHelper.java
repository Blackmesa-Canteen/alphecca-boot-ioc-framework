package io.swen90007sm2.app.db.helper;

import io.swen90007sm2.alpheccaboot.core.config.ConfigFileManager;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.app.common.util.Assert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;

/**
 * helper to get database connection, and perform unified CRUD Operation
 * <br/>
 * supports: PostgreSql
 * https://www.jianshu.com/p/3e2535700159
 * @author xiaotian
 */

public class DbHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);
    private static String dbUserName;
    private static String dbPassword;
    private static String dbPoolDriverName;
    private static String dbIp;

    private static String dbDbName;

    private static String dbSchemeName;

    private static String dbUrlFromSystemEnv;

    private static final ThreadLocal<Connection> currentDbConnection = new ThreadLocal<>();


    static {
        try {
            getDbInfoFromSystemEnv();
        } catch (URISyntaxException | RuntimeException e) {
            LOGGER.info("env db config is invalid, read db config from config file.");
            getDbInfoFromConfigFile();
        }
    }

    /**
     * get db info from heroku server system environment varaibles
     */
    private static void getDbInfoFromSystemEnv() throws URISyntaxException, RuntimeException {
        dbPoolDriverName = ConfigFileManager.getDbDriver();
        String database_url = System.getenv("DATABASE_URL");
        if (StringUtils.isEmpty(database_url)) {
            throw new RuntimeException("DATABASE_URL system env not exist");
        }
        URI dbUri = new URI(database_url);
        dbUserName = dbUri.getUserInfo().split(":")[0];
        dbPassword = dbUri.getUserInfo().split(":")[1];
        dbSchemeName = ConfigFileManager.getDbSchemeName();
        Assert.hasText(dbSchemeName, "configuration dbSchemeName field missing!");
        dbUrlFromSystemEnv = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +
                "?sslmode=require&currentSchema=" + dbSchemeName;
    }

    /**
     * get db info from config file
     */
    private static void getDbInfoFromConfigFile(){
        dbPoolDriverName = ConfigFileManager.getDbDriver();
        Assert.hasText(dbPoolDriverName, "configuration DbDriver field missing!");
        dbIp = ConfigFileManager.getDbIp();
        Assert.hasText(dbPoolDriverName, "configuration DbHostname field missing!");
        dbUserName = ConfigFileManager.getDbUsername();
        Assert.hasText(dbPoolDriverName, "configuration DbUsername field missing!");
        dbPassword = ConfigFileManager.getDbPassword();
        Assert.hasText(dbPoolDriverName, "configuration DbPassword field missing!");
        dbDbName = ConfigFileManager.getDbDbName();
        Assert.hasText(dbDbName, "configuration dbDbName field missing!");
        dbSchemeName = ConfigFileManager.getDbSchemeName();
        Assert.hasText(dbSchemeName, "configuration dbSchemeName field missing!");
    }

    /**
     * init thread private database connection
     */
    public static void initConnection() {
        if (currentDbConnection.get() == null) {
            try {
                Class.forName(dbPoolDriverName);
                if (StringUtils.isEmpty(dbUrlFromSystemEnv)) {
                    // get connection from config file
                    String dbUrl = "jdbc:postgresql://" + dbIp + "/" + dbDbName + "?currentSchema=" + dbSchemeName;
                    // get connection
                    Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
                    // disable auto commit
                    connection.setAutoCommit(false);
                    setConnection(connection);
                } else {
                    // get connection
                    Connection connection = DriverManager.getConnection(dbUrlFromSystemEnv, dbUserName, dbPassword);
                    // disable auto commit
                    connection.setAutoCommit(false);
                    // get connection from system env, used for heroku server
                    setConnection(connection);
                }

            } catch (Exception e) {
                LOGGER.error("get database connection error. ", e);
                throw new InternalException("get database connection error.");
            }
        }
    }

    /**
     * set current connection
     */
    public static void setConnection(Connection connection) {
        currentDbConnection.set(connection);
    }

    /**
     * get connection pool connection
     *
     * @return database connection
     */
    public static Connection getConnection() {
        // get connection from postgre connection pool
//        try {
//            Class.forName(dbPoolDriverName);
//            if (StringUtils.isEmpty(dbUrlFromSystemEnv)) {
//                // get connection from config file
//                String dbUrl = "jdbc:postgresql://" + dbIp + "/" + dbDbName + "?currentSchema=" + dbSchemeName;
//                return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
//            } else {
//                // get connection from system env, used for heroku server
//                return DriverManager.getConnection(dbUrlFromSystemEnv, dbUserName, dbPassword);
//            }
//
//        } catch (Exception e) {
//            LOGGER.error("get database connection error. ", e);
//            throw new InternalException("get database connection error.");
//        }
        Connection connection = currentDbConnection.get();
        if (connection == null) {
            initConnection();
        }
        return currentDbConnection.get();
    }

    /**
     * close db resource connection.
     *
     * It won't close db connection
     */
    public static void closeDbResource(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
//            if (conn != null) {
//                conn.close();
//            }
        } catch (SQLException e) {
            LOGGER.error("Close db resource connection error: ", e);
        }
    }

    /**
     * close database connection
     */
    public static void closeConnection() {
        if (currentDbConnection.get() != null) {
            try {
                currentDbConnection.get().close();
                setConnection(null);
            } catch (SQLException e) {
                LOGGER.error("Close db connection error: ", e);
            }
        }
    }

    /**
     * release batched db connection
     */
    public static void closeDbResourceBatch(Connection conn, List<PreparedStatement> pstmtList, List<ResultSet> rsList) {
        try {
            if (rsList != null) {
                for (ResultSet rs: rsList) {
                    if (rs != null) rs.close();
                }
            }

            if (pstmtList != null) {
                for (PreparedStatement pstmt : pstmtList) {
                    if (pstmt != null) pstmt.close();
                }
            }

//            if (conn != null) {
//                conn.close();
//            }
        } catch (SQLException e) {
            LOGGER.error("Close db connection error: ", e);
        }
    }
}
