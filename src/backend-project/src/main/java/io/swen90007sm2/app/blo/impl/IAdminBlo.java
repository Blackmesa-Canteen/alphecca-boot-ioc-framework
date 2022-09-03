package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.app.model.entity.Admin;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;

public interface IAdminBlo {

    AuthToken doLoginAndGenToken(LoginParam loginParam);

    void doLogout(AuthToken authToken);

    void doUpdateUserWithoutPassword(String userId, UserUpdateParam userUpdateParam);

    void doUpdateUserPassword(String userId, String originalPassword, String newPassword);

    Admin getAdminInfoByToken(String header);

    Admin getAdminInfoByUserId(String userId);

}
