package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;

import javax.servlet.http.HttpServletRequest;

public interface IHotelierBlo {
    AuthToken doLoginAndGenToken(LoginParam loginParam);

    void doRegisterUser(UserRegisterParam userRegisterParam);

    void doLogout(AuthToken authToken);

    void doUpdateUserPassword(String userId, String originalPassword, String newPassword);

    Hotelier getHotelierInfoByToken(String header);

    Hotelier getHotelierInfoByUserId(String userId);
    PageBean<Hotelier> getHotelierByPage(int pageNo, int pageSize);

    void doUpdateUserExceptPassword(String userId, UserUpdateParam param);
}
