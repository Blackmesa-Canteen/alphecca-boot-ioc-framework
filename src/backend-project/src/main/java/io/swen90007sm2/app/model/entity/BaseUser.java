package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * @author 996Worker
 * @description base entity for user of this app
 * @create 2022-08-05 14:37
 */
public abstract class BaseUser extends BaseEntity {

    private String userId;

    private String userName;

    private String description;

    private String password;

    private String avatarUrl;

    public BaseUser() {
    }

    public BaseUser(String userId, String userName, String description, String password, String avatarUrl) {
        this.userId = userId;
        this.userName = userName;
        this.description = description;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public BaseUser(Date createTime, Date updateTime, String userId, String userName, String description, String password, String avatarUrl) {
        super(createTime, updateTime);
        this.userId = userId;
        this.userName = userName;
        this.description = description;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}