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
 *
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

    private final Logger logger = LoggerFactory.getLogger(DbHelper.class);

    private final String datasource = "";// data source from pool
    private final DBType type = DBType.PostgreSql;
    private final String dbUserName;
    private final String dbPassword;
    private final String dbPoolDriverName;
    private final String dbIp;

    private final String dbSchemeName;


    private Connection conn = null;
    private PreparedStatement pstmt = null;
    protected ResultSet rs = null;

    public DbHelper() {
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
     */
    private void getConnection() {
        // get connection from postgre connection pool
        try {
            Class.forName(dbPoolDriverName);
            String dbUrl = "jdbc:postgresql://" + dbIp + "/" + dbSchemeName;
            conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        } catch (Exception e) {
            logger.error("get database connection error. ", e);
            throw new InternalException("get database connection error: " + e.toString());
        }
    }

    /**
     * close db connection
     */
    private void closeAll() {
        try {
            if (this.rs != null) {
                rs.close();
            }
            if (this.pstmt != null) {
                pstmt.close();
            }
            if (this.conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("Close db connection error: ", e);
        }
    }

    // TODO CRUD
}
