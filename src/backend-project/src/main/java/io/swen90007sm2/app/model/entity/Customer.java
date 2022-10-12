package io.swen90007sm2.app.model.entity;

import io.swen90007sm2.app.db.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * Customer Database Entity
 *
 * @author xiaotian
 */
public class Customer extends BaseUser {

    // bookings
    @Transient
    List<Transaction> transactions;

    public Customer() {
    }

    public Customer(String userId, String name, String description, String password, String avatarUrl) {
        super(userId, name, description, password, avatarUrl);
    }

    public Customer(Date createTime, Date updateTime, String userId, String name, String description, String password, String avatarUrl) {
        super(createTime, updateTime, userId, name, description, password, avatarUrl);
    }


}
