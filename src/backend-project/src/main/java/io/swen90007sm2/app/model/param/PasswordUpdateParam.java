package io.swen90007sm2.app.model.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordUpdateParam {

    @NotNull(message = "New password should be not null")
    @Size(max = 100, message = "User password should not exceed {max}")
    String newPassword;

    @NotNull(message = "original password should be not null")
    @Size(max = 100, message = "User password should not exceed {max}")
    String originalPassword;

    public PasswordUpdateParam() {
    }

    public PasswordUpdateParam(String newPassword, String originalPassword) {
        this.newPassword = newPassword;
        this.originalPassword = originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }
}
