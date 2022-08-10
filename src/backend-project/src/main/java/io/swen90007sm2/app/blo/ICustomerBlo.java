package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.security.bean.AuthToken;

public interface ICustomerBlo {

    /**
     * Get JWT token if the loginParam is correct
     * @param loginParam loginParam from handler
     * @return valid token. If null. loginParam is not valid
     */
    AuthToken doLoginAndGenToken(LoginParam loginParam) throws Exception;
}
