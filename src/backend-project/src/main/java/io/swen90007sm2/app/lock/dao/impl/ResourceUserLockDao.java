package io.swen90007sm2.app.lock.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.lock.dao.IResourceUserLockDao;
import io.swen90007sm2.app.lock.entity.ResourceUserLock;
import io.swen90007sm2.app.lock.exception.ResourceConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.swen90007sm2.app.db.helper.DbHelper.closeDbResource;
import static io.swen90007sm2.app.db.helper.DbHelper.getConnection;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-20 16:48
 */
@Dao
public class ResourceUserLockDao implements IResourceUserLockDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUserLockDao.class);

    @Override
    public int insertOne(ResourceUserLock entity) {
        String sql = "INSERT INTO resource_user_lock (resource_id, user_id) values (?, ?)";
        Object[] params = {entity.getResourceId(), entity.getUserId()};

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                // parameterIndex – the first parameter is 1, the second is 2, ...
                preparedStatement.setObject(i + 1, params[i]);
            }

            LOGGER.info("Execute non query SQL [{}] with param [{}]", sql, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Resource lock insertion exception. in [{}] with param [{}]", sql, params, e);
            throw new ResourceConflictException("Resource Lock: the public data is accessed by the other user, " +
                    "please try again later");
        } finally {
            closeDbResource(conn, preparedStatement, null);
        }
    }

    @Override
    public int deleteOne(Integer resourceId, String userId) {
        String sql = "DELETE FROM resource_user_lock WHERE resource_id = ? and user_id = ?";
        Object[] params = {resourceId, userId};

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                // parameterIndex – the first parameter is 1, the second is 2, ...
                preparedStatement.setObject(i + 1, params[i]);
            }

            LOGGER.info("Execute non query SQL [{}] with param [{}]", sql, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Resource lock deletion exception. in [{}] with param [{}]", sql, params, e);
            throw new ResourceConflictException("Can not release the resource user lock.");
        } finally {
            closeDbResource(conn, preparedStatement, null);
        }
    }
}