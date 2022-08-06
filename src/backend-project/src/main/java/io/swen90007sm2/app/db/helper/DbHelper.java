package io.swen90007sm2.app.db.helper;

import io.swen90007sm2.alpheccaboot.core.config.ConfigFileManager;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.app.common.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * helper to get database connection, and perform unified CRUD Operation
 * <br/>
 * supports: PostgreSql
 * https://www.jianshu.com/p/3e2535700159
 * @author xiaotian
 */

public class DbHelper {

    /**
     * db type
     */
    static enum DBType {
        MySQL,
        Oracle,
        PostgreSql,
        SQLite,
        H2,
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);

    private static final String datasource = "";// data source from pool
    private static final DBType type = DBType.PostgreSql;
    private static final String dbUserName;
    private static final String dbPassword;
    private static final String dbPoolDriverName;
    private static final String dbIp;

    private static final String dbSchemeName;


    static {
        dbPoolDriverName = ConfigFileManager.getDbDriver();
        Assert.hasText(dbPoolDriverName, "configuration DbDriver field missing!");
        dbIp = ConfigFileManager.getDbIp();
        Assert.hasText(dbPoolDriverName, "configuration DbHostname field missing!");
        dbUserName = ConfigFileManager.getDbUsername();
        Assert.hasText(dbPoolDriverName, "configuration DbUsername field missing!");
        dbPassword = ConfigFileManager.getDbPassword();
        Assert.hasText(dbPoolDriverName, "configuration DbPassword field missing!");
        dbSchemeName = ConfigFileManager.getDbSchemeName();
        Assert.hasText(dbSchemeName, "configuration dbSchemeName field missing!");
    }

    /**
     * get connection pool connection
     *
     * @return database connection
     */
    public static Connection getConnection() {
        // get connection from postgre connection pool
        try {
            Class.forName(dbPoolDriverName);
            String dbUrl = "jdbc:postgresql://" + dbIp + "/" + dbSchemeName;
            return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        } catch (Exception e) {
            LOGGER.error("get database connection error. ", e);
            throw new InternalException("get database connection error.");
        }
    }

    /**
     * close db connection
     */
    public static void closeDbResource(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Close db connection error: ", e);
        }
    }
}
