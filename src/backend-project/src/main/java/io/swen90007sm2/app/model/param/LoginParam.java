package io.swen90007sm2.app.model.param;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginParam {

    @NotBlank(message = "User login email ID should be not null")
    @Size(max = 255, message = "User Login ID should not exceed {max}")
    @Email(message = "User login ID should be in Email format")
    private String userId;

    @NotBlank(message = "User login password should be not null")
    @Size(max = 100, message = "User Login password should not exceed {max}")
    private String password;

    public LoginParam() {
    }

    public LoginParam(String userId, String password) {
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
        return "LoginParam{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
