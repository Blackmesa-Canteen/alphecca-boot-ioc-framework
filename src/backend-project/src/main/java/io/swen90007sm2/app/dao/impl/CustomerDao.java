package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Customer;

@Dao
public class CustomerDao implements ICustomerDao {

    @Override
    public Customer findCustomerByUserId(String userId) {
        Customer customerBean = CRUDTemplate.executeQueryWithOneRes(
                Customer.class,
                "SELECT * FROM customer WHERE user_id = ?",
                userId
        );

        return customerBean;
    }

    @Override
    public int addNewCustomer(Customer customer) {
        int row = CRUDTemplate.executeNonQuery(
                "INSERT INTO customer (user_id, password, description, user_name) values (?, ?, ?, ?)",
                customer.getUserId(),
                customer.getPassword(),
                customer.getDescription(),
                customer.getUserName()
        );

        return row;
    }

    @Override
    public int updateCustomer(String customerId, Customer customer) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE customer SET description=?, user_name=?, avatar_url=?, update_time=? WHERE user_id = ?",
                customer.getDescription(),
                customer.getUserName(),
                customer.getAvatarUrl(),
                new java.sql.Date(TimeUtil.now().getTime()),
                customerId
        );

        return row;
    }

    @Override
    public int updatePassword(String customerId, String newCypher) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE customer SET password=?, update_time=? WHERE user_id = ?",
                newCypher,
                new java.sql.Date(TimeUtil.now().getTime()),
                customerId
        );

        return row;
    }

}
