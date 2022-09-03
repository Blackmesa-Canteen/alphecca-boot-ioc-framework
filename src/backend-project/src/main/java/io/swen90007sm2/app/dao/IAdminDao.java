package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Admin;

public interface IAdminDao extends IBaseDao<Admin> {

    Admin findOneByBusinessId(String userId);

    int insertOne(Admin admin);

    int updateOne(Admin admin);

    int deleteOne(Admin admin);

}
