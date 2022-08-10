package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.EncryptUtil;

import java.util.concurrent.TimeUnit;

@Blo
public class CustomerBlo implements ICustomerBlo {

    @AutoInjected
    ICustomerDao customerDao;

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    @Override
    public AuthToken doLoginAndGenToken(LoginParam loginParam) throws Exception {



        // get role from db
        String userId = loginParam.getUserId();
        String password = loginParam.getPassword();
        Customer customer = customerDao.findCustomerByUserId(userId);

        // if no such customer
        if (customer == null) {
            throw new RequestException (
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode());
        }

        // check username password with db
        String cypherRecord = customer.getPassword();
        boolean isPasswordMatched = EncryptUtil.isOriginMatchCypher(password, cypherRecord);
        if (!isPasswordMatched) {
            throw new RequestException (
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode());
        }

        // generate token
        String token = TokenHelper.genAuthToken(userId, AuthRole.CUSTOMER_ROLE);

        // store the token into cache with expiration time
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        cache.put(
                CacheConstant.TOKEN_KEY_PREFIX + userId,
                authToken,
                SecurityConstant.DEFAULT_TOKEN_EXPIRATION_TIME_MS,
                TimeUnit.MILLISECONDS);

        // return the token string
        return authToken;
    }

    @Override
    public Customer getUserInfoBasedOnToken(String tokenString) {
        AuthToken authToken = TokenHelper.parseAuthTokenString(tokenString);
        String userId = authToken.getUserId();
        Customer customerBean = customerDao.findCustomerByUserId(userId);
        // remove sensitive info
        customerBean.setPassword(null);
        return customerBean;
    }

    @Override
    public void doRegisterUser(UserRegisterParam registerParam) {
        String userName = registerParam.getUserName();
        String userId = registerParam.getUserId();

        // check existence
        Customer prevResult = customerDao.findCustomerByUserId(userId);
        if (prevResult != null) {
            throw new RequestException(
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getCode()
            );
        }

        // encrypt password before store it in db
        String cypher = EncryptUtil.encrypt(registerParam.getPassword());

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setUserName(userName);
        customer.setPassword(cypher);
        customer.setDescription("New User");

        customerDao.addNewCustomer(customer);
    }
}
