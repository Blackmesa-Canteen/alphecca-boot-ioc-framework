package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.param.LoginParam;

public interface ICustomerBlo {

    /**
     * Get JWT token if the loginParam is correct
     * @param loginParam loginParam from handler
     * @return valid token. If null. loginParam is not valid
     */
    String getTokenByLoginParam(LoginParam loginParam) throws Exception;
}
