package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.alpheccaboot.exception.ResourceNotFoundException;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IAdminDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.lock.exception.ResourceConflictException;
import io.swen90007sm2.app.model.entity.Admin;
import io.swen90007sm2.app.model.pojo.VersionInfoBean;

@Dao
@Lazy

/* The adminDao is not used, created for future possible use */
public class AdminDao implements IAdminDao {
    @Override
    public Admin findOneByBusinessId(String userId) {
        Admin adminBean = CRUDTemplate.executeQueryWithOneRes(
                Admin.class,
                "SELECT * FROM admin WHERE user_id = ?",
                userId
        );

        return adminBean;
    }

    @Override
    public int insertOne(Admin admin) {
        int row = CRUDTemplate.executeNonQuery(
                "INSERT INTO admin (id, user_id, password, description, user_name) values (?, ?, ?, ?, ?)",
                admin.getId(),
                admin.getUserId(),
                admin.getPassword(),
                admin.getDescription(),
                admin.getUserName()
        );
        return row;
    }

    @Override
    public int updateOne(Admin admin) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE admin SET password = ?, description = ?, user_name = ?, avatar_url = ?, update_time = ?, version = ? " +
                        "WHERE id = ? and version = ?",
                admin.getPassword(),
                admin.getDescription(),
                admin.getUserName(),
                admin.getAvatarUrl(),
                new java.sql.Date(TimeUtil.now().getTime()),
                admin.getVersion() + 1,
                admin.getId(),
                admin.getVersion()
        );

        if (row == 0) {
            throwConcurrencyException(admin);
        }
        return row;
    }

    @Override
    public int deleteOne(Admin admin) {
        int row = CRUDTemplate.executeNonQuery(
                "DELETE FROM admin WHERE id = ?",
                admin.getId()
        );
        return row;
    }

    @Override
    public void throwConcurrencyException(Admin entity) {
        VersionInfoBean currentVersion = CRUDTemplate.executeQueryWithOneRes(
                VersionInfoBean.class,
                "SELECT version, update_time FROM admin WHERE id = ?",
                entity.getId()
        );

        if (currentVersion == null) {
            throw new ResourceNotFoundException(
                    "admin " + entity.getId() + " has been deleted"
            );
        }

        if (currentVersion.getVersion() == null) {
            throw new InternalException(
                    "Missing version info for optimistic concurrency control in admin"
            );
        }

        if (currentVersion.getVersion() > entity.getVersion()) {
            throw new ResourceConflictException(
                    "Rejected: admin " + entity.getId() + " has been modified by others at "
                            + currentVersion.getUpdateTime()
            );
        } else {
            throw new InternalException(
                    "unexpected error in throwConcurrencyException for admin " + entity.getId()
            );
        }
    }
}
