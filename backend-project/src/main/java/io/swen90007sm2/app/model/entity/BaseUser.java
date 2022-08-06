package io.swen90007sm2.app.model.entity;

import java.util.Date;

/**
 * @author 996Worker
 * @description base entity for user of this app
 * @create 2022-08-05 14:37
 */
public abstract class BaseUser extends BaseEntity {

    private String loginId;

    private String password;

    private String avatarUrl;

    public BaseUser() {
    }

    public BaseUser(String loginId, String password, String avatarUrl) {
        this.loginId = loginId;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public BaseUser(Date createTime, Date updateTime, String loginId, String password, String avatarUrl) {
        super(createTime, updateTime);
        this.loginId = loginId;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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