package io.swen90007sm2.app.model.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserUpdateParam {
    @Size(max = 50, message = "User nick name should not exceed {max}")
    String userName;

    @Size(max = 255, message = "User description should not exceed {max}")
    String description;

    String avatarUrl;

    public UserUpdateParam() {
    }

    public UserUpdateParam(String userName, String description, String avatarUrl) {
        this.userName = userName;
        this.description = description;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
