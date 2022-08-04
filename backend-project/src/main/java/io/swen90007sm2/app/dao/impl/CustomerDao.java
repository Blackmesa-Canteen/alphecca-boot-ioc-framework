package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.model.entity.Customer;

@Dao
public class CustomerDao implements ICustomerDao {

    @Override
    public Customer findCustomerByUserId(String UserId) {
        return null;
    }
}
