package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
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
}
