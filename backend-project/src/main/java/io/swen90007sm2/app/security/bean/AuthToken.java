package io.swen90007sm2.app.security.bean;

import java.util.Date;

/**
 * @author 996Worker
 * @description token for JWT authentication
 * @create 2022-08-03 23:35
 */
public class AuthToken {

    private String token;

    private String roleName;

    private String userId;

    private Date issuedDate;

    private Date expirationDate;

    public AuthToken() {
    }

    public AuthToken(String token, String roleName, String userId, Date issuedDate, Date expirationDate) {
        this.token = token;
        this.roleName = roleName;
        this.userId = userId;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", role='" + roleName + '\'' +
                ", userName='" + userId + '\'' +
                ", issuedDate=" + issuedDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}