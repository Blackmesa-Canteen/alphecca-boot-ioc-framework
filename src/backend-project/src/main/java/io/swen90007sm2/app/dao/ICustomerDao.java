package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Customer;

/**
 * data access layer for Customer
 *
 * @author xiaotian
 */
public interface ICustomerDao {

    /**
     * Find Customer in database by userID
     * @param UserId userId
     * @return Customer Entity
     */
    Customer findCustomerByUserId(String UserId);

    /**
     * insert a new customer in db
     * @param customer new Customer obj
     * @return 1 if successed
     */
    int addNewCustomer(Customer customer);

    /**
     * update Customer, except password
     * @param customerId target id
     * @param customer new obj
     * @return num of influenced rows
     */
    int updateCustomer(String customerId, Customer customer);

    /**
     * update password cypher
     * @param customerId target userId
     * @param newCypher pwd cypher
     * @return num of influenced rows
     */
    int updatePassword(String customerId, String newCypher);
}
