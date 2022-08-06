package io.swen90007sm2.app.db.util;

import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.app.db.helper.DbHelper;
import io.swen90007sm2.app.db.resolver.BeanListResultSetResolver;
import io.swen90007sm2.app.db.resolver.BeanResultSetResolver;
import io.swen90007sm2.app.db.resolver.IResultSetResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static io.swen90007sm2.app.db.helper.DbHelper.*;

/**
 * CRUD template
 *
 * @author xiaotain li
 */
public class CRUDTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CRUDTemplate.class);
    /**
     * execute sql of Update, deletion
     * @return row num influenced
     */
    public static int executeNonQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    // parameterIndex – the first parameter is 1, the second is 2, ...
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }

            LOGGER.info("Execute non query SQL [{}] with param [{}]", sql, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Execute SQL ERROR in [{}] with param [{}]", sql, params);
            throw new InternalException("database error， try again later");
        } finally {
            closeDbResource(conn, preparedStatement, null);
        }
    }

    /**
     * parform query
     * @param beanType bean type of return
     * @param sql sql
     * @param params input param
     * @return Bean or beanList
     */
    @SuppressWarnings("unchecked")
    public static <T> T executeQuery(Class<T> beanType, String sql, Object... params) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i +1, params[i]);
            }

            resultSet = preparedStatement.executeQuery();
            if (resultSet == null || resultSet.getFetchSize() == 0) {
                return null;
            } else if (resultSet.getFetchSize() == 1) {
                // one result bean
                BeanResultSetResolver<T> resolver = new BeanResultSetResolver<>(beanType);
                return resolver.resolveResultSet(resultSet);
            } else {
                // multiple result bean
                BeanListResultSetResolver<T> resolver = new BeanListResultSetResolver<>(beanType);
                return (T) resolver.resolveResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Execute SQL ERROR in [{}] with param [{}]", sql, params);
            throw new InternalException("database error， try again later");
        } catch (Exception e) {
            LOGGER.error("ResolveResultSet ERROR.", e);
            throw new InternalException("database error， try again later");
        } finally {
            closeDbResource(conn, preparedStatement, resultSet);
        }
    }
}
