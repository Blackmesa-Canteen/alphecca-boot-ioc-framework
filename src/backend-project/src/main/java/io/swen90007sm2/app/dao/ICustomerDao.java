package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Customer;

import java.util.List;

/**
 * data access layer for Customer
 *
 * @author xiaotian
 */
public interface ICustomerDao extends IBaseDao<Customer> {


    /**
     * Find total record counts.
     * @return
     */
    int findTotalCount();

    /**
     * find customers by page
     * @param start result starts from which row
     * @param rows total rows needed
     * @return list
     */
    List<Customer> findAllByPage(Integer start, Integer rows);

    /**
     * Find Customer in database by userID
     * @param userId is business specific Id, not database id, in this case, is userId
     * @return Customer Entity
     */
    Customer findOneByBusinessId(String userId);

    /**
     * insert a new customer in db
     * @param customer new Customer obj
     * @return 1 if successed
     */
    int insertOne(Customer customer);

    /**
     * update Customer
     * @param customerId target id
     * @param customer new obj
     * @return num of influenced rows
     */
    int updateOne(Customer customer);

    /**
     * delete one customer by customerId
     * @param customerId customerID
     * @return rows
     */
    int deleteOne(Customer customer);

    /**
     * update password cypher
     * @param customerId target userId
     * @param newCypher pwd cypher
     * @return num of influenced rows
     */
    @Deprecated
    int updatePasswordOne(String customerId, String newCypher);


}
