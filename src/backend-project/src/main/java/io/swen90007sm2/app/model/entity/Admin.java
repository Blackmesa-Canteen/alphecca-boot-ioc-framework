package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class Admin extends BaseUser {

    public Admin() {
    }

    public Admin(String userId, String name, String description, String password, String avatarUrl) {
        super(userId, name, description, password, avatarUrl);
    }

    public Admin(Date createTime, Date updateTime, String userId, String name, String description, String password, String avatarUrl) {
        super(createTime, updateTime, userId, name, description, password, avatarUrl);
    }

}
