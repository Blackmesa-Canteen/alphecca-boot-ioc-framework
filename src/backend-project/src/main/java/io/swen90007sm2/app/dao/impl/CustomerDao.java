package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.alpheccaboot.exception.ResourceNotFoundException;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.lock.exception.ResourceConflictException;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.pojo.VersionInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Dao
@Lazy
public class CustomerDao implements ICustomerDao {

    @Override
    public int findTotalCount() {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM customer");

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public List<Customer> findAllByPage(Integer start, Integer rows) {
        List<Customer> customers = CRUDTemplate.executeQueryWithMultiRes(
                Customer.class,
                "SELECT * FROM customer OFFSET ? LIMIT ?",
                start,
                rows
        );

        return customers;
    }

    @Override
    public Customer findOneByBusinessId(String userId) {
        Customer customerBean = CRUDTemplate.executeQueryWithOneRes(
                Customer.class,
                "SELECT * FROM customer WHERE user_id = ?",
                userId
        );

        return customerBean;
    }

    @Override
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

    @Override
    public int updateOne(Customer customer) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE customer SET password = ?, description=?, user_name=?, avatar_url=?, update_time=?, version=? " +
                        "WHERE id = ? and version = ?",
                customer.getPassword(),
                customer.getDescription(),
                customer.getUserName(),
                customer.getAvatarUrl(),
                new java.sql.Date(TimeUtil.now().getTime()),
                customer.getVersion() + 1,
                customer.getId(),
                customer.getVersion()
        );

        if (row == 0) {
            throwConcurrencyException(customer);
        }

        return row;
    }

    @Override
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

    @Override
    public void throwConcurrencyException(Customer entity) {
        VersionInfoBean currentVersion = CRUDTemplate.executeQueryWithOneRes(
                VersionInfoBean.class,
                "SELECT version, update_time FROM customer WHERE id = ?",
                entity.getId()
        );

        if (currentVersion == null) {
            throw new ResourceNotFoundException(
                    "customer " + entity.getId() + " has been deleted"
            );
        }

        if (currentVersion.getVersion() == null) {
            throw new InternalException(
                    "Missing version info for optimistic concurrency control in customer"
            );
        }

        if (currentVersion.getVersion() > entity.getVersion()) {
            throw new ResourceConflictException(
                    "Rejected: customer " + entity.getId() + " has been modified by others at "
                            + currentVersion.getUpdateTime()
            );
        } else {
            throw new InternalException(
                    "unexpected error in throwConcurrencyException for customer " + entity.getId()
            );
        }
    }

    @Override
    public int deleteOne(Customer customer) {
        int row = CRUDTemplate.executeNonQuery(
                "DELETE FROM customer WHERE id = ?",
                customer.getId()
        );

        return row;
    }

}
