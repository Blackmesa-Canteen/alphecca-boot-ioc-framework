package io.swen90007sm2.app.model.param;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 996Worker
 * @description registration Param for customer, and hotelier
 * @create 2022-08-10 23:30
 */
public class UserRegisterParam {
    @NotBlank(message = "User login email ID should be not null")
    @Size(max = 255, message = "User Login ID should not exceed {max}")
    @Email(message = "User login ID should be in Email format")
    String userId;

    @NotBlank(message = "User nickname should be not null")
    @Size(max = 50, message = "User nick name should not exceed {max}")
    String userName;

    @NotBlank(message = "User password should be not null")
    @Size(max = 100, message = "User password should not exceed {max}")
    String password;

    public UserRegisterParam() {
    }

    public UserRegisterParam(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}