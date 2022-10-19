package io.swen90007sm2.app.lock.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.db.resolver.BeanResultSetResolver;
import io.swen90007sm2.app.lock.dao.IResourceUserLockDao;
import io.swen90007sm2.app.lock.entity.ResourceUserLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static io.swen90007sm2.app.db.helper.DbHelper.*;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-20 16:48
 */
@Dao
public class ResourceUserLockDao implements IResourceUserLockDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUserLockDao.class);

    @Override
    public ResourceUserLock findOneLockByResourceId(String resourceId) {
        String sql = "SELECT * FROM resource_user_lock WHERE resource_id = ?";
        Object[] params = {resourceId};

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i +1, params[i]);
            }

            LOGGER.info("Execute non query SQL [{}] with param [{}]", sql, params);
            resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                return null;
            } else  {
                // one result bean
                BeanResultSetResolver<ResourceUserLock> resolver = new BeanResultSetResolver<>(ResourceUserLock.class);
                return resolver.resolveResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Execute SQL ERROR in [{}] with param [{}]", sql, params, e);
            throw new InternalException("database error， try again later");
        } catch (Exception e) {
            LOGGER.error("ResolveResultSet ERROR.", e);
            throw new InternalException("database error， try again later", e);
        } finally {
            closeDbResource(conn, preparedStatement, resultSet);
        }
    }

    @Override
    public int insertOne(ResourceUserLock entity) {
        String sql = "INSERT INTO resource_user_lock (resource_id, user_id, lock_type, create_time) values (?, ?, ?, ?)";
        Object[] params = {entity.getResourceId(), entity.getUserId(), entity.getLockType(),
                new java.sql.Timestamp(TimeUtil.now().getTime())};

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getAutoCommitConnection();
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                // parameterIndex – the first parameter is 1, the second is 2, ...
                preparedStatement.setObject(i + 1, params[i]);
            }

            LOGGER.info("Execute non query SQL [{}] with param [{}]", sql, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Resource lock insertion exception. in [{}] with param [{}]", sql, params, e);
            throw new InternalException("Resource Lock: db insertion error" +
                    "please try again later");
        } finally {
            closeDbResource(conn, preparedStatement, null);
            releaseConnection(conn);
        }
    }

    @Override
    public int deleteOneByResourceId(String resourceId) {
        String sql = "DELETE FROM resource_user_lock WHERE resource_id = ?";
        Object[] params = {resourceId};

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getAutoCommitConnection();
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                // parameterIndex – the first parameter is 1, the second is 2, ...
                preparedStatement.setObject(i + 1, params[i]);
            }

            LOGGER.info("Execute non query SQL [{}] with param [{}]", sql, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Resource lock deletion exception. in [{}] with param [{}]", sql, params, e);
            throw new InternalException("Can not release the resource user lock.");
        } finally {
            closeDbResource(conn, preparedStatement, null);
            releaseConnection(conn);
        }
    }

    @Override
    public int deleteOneByResourceAndUser(String resourceId, String userId) {
        String sql = "DELETE FROM resource_user_lock WHERE resource_id = ? and user_id = ?";
        Object[] params = {resourceId, userId};

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getAutoCommitConnection();
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                // parameterIndex – the first parameter is 1, the second is 2, ...
                preparedStatement.setObject(i + 1, params[i]);
            }

            LOGGER.info("Execute non query SQL [{}] with param [{}]", sql, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Resource lock deletion exception. in [{}] with param [{}]", sql, params, e);
            throw new InternalException("Can not release the resource user lock.");
        } finally {
            closeDbResource(conn, preparedStatement, null);
            releaseConnection(conn);
        }
    }
}