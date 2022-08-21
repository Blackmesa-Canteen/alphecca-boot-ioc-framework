package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;

import javax.servlet.http.HttpServletRequest;

public interface IHotelierBlo {
    AuthToken doLoginAndGenToken(LoginParam loginParam);

    void doRegisterUser(UserRegisterParam userRegisterParam);

    void doLogout(HttpServletRequest request);

    void doUpdateUserPassword(HttpServletRequest request, String originalPassword, String newPassword);

    Hotelier getHotelierInfoByToken(String header);

    Hotelier getHotelierInfoByUserId(String userId);
}
