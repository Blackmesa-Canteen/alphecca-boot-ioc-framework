package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * Customer Database Entity
 *
 * @author xiaotian
 */
public class Customer extends BaseEntity {

    private String userId;

    private String password;

    public Customer() {
    }

    public Customer(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Customer(Date createTime, Date updateTime, String userId, String password) {
        super(createTime, updateTime);
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
