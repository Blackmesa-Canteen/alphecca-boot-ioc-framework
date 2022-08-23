package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.security.helper.TokenHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Dao
@Lazy
public class CustomerDao implements ICustomerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDao.class);

    @Override
    public int findTotalCount() {
        return CustomerMapper.getInstance().findTotalCount();
    }

    @Override
    public List<Customer> findAllByPage(Integer start, Integer rows) {
        return CustomerMapper.getInstance().findAllByPage(start, rows);
    }

    @Override
    public Customer findOneByBusinessId(String userId) {
        return CustomerMapper.getInstance().findOneByBusinessId(userId);
    }

    @Override
    public int insertOne(Customer customer) {
        return CustomerMapper.getInstance().insertOne(customer);
    }

    @Override
    public int updateOne(Customer customer) {
        return CustomerMapper.getInstance().updateOne(customer);
    }

    @Override
    public int deleteOne(Customer customer) {
        return CustomerMapper.getInstance().deleteOne(customer);
    }

    @Override
    @Deprecated
    public int updatePasswordOne(String customerId, String newCypher) {
        return CustomerMapper.getInstance().updatePasswordOne(customerId, newCypher);
    }

    /**
     * Lazy-loaded Singleton Mapper Class.
     */
    private static class CustomerMapper {
        private static volatile CustomerMapper instance = null;
        private static CustomerMapper getInstance() {

            if (instance == null) {
                synchronized (CustomerMapper.class) {
                    // double check to prevent duplicate instance
                    if (instance == null) {
                        instance = new CustomerMapper();
                        LOGGER.info("Lazy loaded: [{}]", instance.getClass().getName());
                    }
                }
            }

            return instance;
        }

        public int findTotalCount() {
            Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                    Long.class,
                    "SELECT count(*) FROM customer");

            return (totalRows != null) ? totalRows.intValue() : 0;
        }

        public List<Customer> findAllByPage(Integer start, Integer rows) {
            List<Customer> customers = CRUDTemplate.executeQueryWithMultiRes(
                    Customer.class,
                    "SELECT * FROM customer OFFSET ? LIMIT ?",
                    start,
                    rows
            );

            return customers;
        }

        public Customer findOneByBusinessId(String userId) {
            Customer customerBean = CRUDTemplate.executeQueryWithOneRes(
                    Customer.class,
                    "SELECT * FROM customer WHERE user_id = ?",
                    userId
            );

            return customerBean;
        }

        public int insertOne(Customer customer) {
            int row = CRUDTemplate.executeNonQuery(
                    "INSERT INTO customer (id, user_id, password, description, user_name) values (?, ?, ?, ?, ?)",
                    customer.getId(),
                    customer.getUserId(),
                    customer.getPassword(),
                    customer.getDescription(),
                    customer.getUserName()
            );

            return row;
        }

        public int updateOne(Customer customer) {
            int row = CRUDTemplate.executeNonQuery(
                    "UPDATE customer SET password = ?, description=?, user_name=?, avatar_url=?, update_time=? WHERE id = ?",
                    customer.getPassword(),
                    customer.getDescription(),
                    customer.getUserName(),
                    customer.getAvatarUrl(),
                    new java.sql.Date(TimeUtil.now().getTime()),
                    customer.getId()
            );

            return row;
        }

        @Deprecated
        public int updatePasswordOne(String customerId, String newCypher) {
            int row = CRUDTemplate.executeNonQuery(
                    "UPDATE customer SET password=?, update_time=? WHERE user_id = ?",
                    newCypher,
                    new java.sql.Date(TimeUtil.now().getTime()),
                    customerId
            );

            return row;
        }

        public int deleteOne(Customer customer) {
            int row = CRUDTemplate.executeNonQuery(
                    "DELETE FROM customer WHERE id = ?",
                    customer.getId()
            );

            return row;
        }
    }


}
