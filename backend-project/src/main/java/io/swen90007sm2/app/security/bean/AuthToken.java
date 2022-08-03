package io.swen90007sm2.app.security.bean;

import java.util.Date;

/**
 * @author 996Worker
 * @description token for JWT authentication
 * @create 2022-08-03 23:35
 */
public class AuthToken {

    private String token;

    private String role;

    private String userName;

    private Date issuedDate;

    private Date expirationDate;

    public AuthToken() {
    }

    public AuthToken(String token, String role, String userName, Date issuedDate, Date expirationDate) {
        this.token = token;
        this.role = role;
        this.userName = userName;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}