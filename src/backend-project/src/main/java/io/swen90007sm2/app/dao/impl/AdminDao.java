package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.IAdminDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Admin;

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
                "UPDATE admin SET password = ?, description = ?, user_name = ? WHERE user_id = ?",
                admin.getPassword(),
                admin.getDescription(),
                admin.getUserName(),
                admin.getAvatarUrl(),
                new java.sql.Date(admin.getCreateTime().getTime()),
                admin.getId()
        );
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

}
