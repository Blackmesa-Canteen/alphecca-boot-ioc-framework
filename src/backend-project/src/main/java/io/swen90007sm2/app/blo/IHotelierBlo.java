package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.security.bean.AuthToken;

public interface IHotelierBlo {
    AuthToken doLoginAndGenToken(LoginParam loginParam);
}
