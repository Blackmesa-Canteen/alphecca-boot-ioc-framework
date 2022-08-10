package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;

public interface ICustomerBlo {

    /**
     * Get JWT token if the loginParam is correct
     * @param loginParam loginParam from controller
     * @return valid token. If null. loginParam is not valid
     */
    AuthToken doLoginAndGenToken(LoginParam loginParam) throws Exception;

    /**
     * get userInfo bean from db with token
     * @param tokenString token string
     * @return Customer bean
     */
    Customer getUserInfoBasedOnToken(String tokenString);

    /**
     * register a new user
     * @param registerParam param
     */
    void doRegisterUser(UserRegisterParam registerParam);
}
