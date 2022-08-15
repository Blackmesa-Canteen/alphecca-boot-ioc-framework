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
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Blo
public class CustomerBlo implements ICustomerBlo {

    @AutoInjected
    ICustomerDao customerDao;

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    @Override
    public AuthToken doLoginAndGenToken(LoginParam loginParam) {
        // get role from db
        String userId = loginParam.getUserId();
        String password = loginParam.getPassword();
        Customer customer = customerDao.findOneByBusinessId(userId);

        // prevent double login
        if (cache.get(CacheConstant.TOKEN_KEY_PREFIX + userId).isPresent()) {
            throw new RequestException(
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getCode()
            );
        }

        // if no such customer
        if (customer == null) {
            throw new RequestException (
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode());
        }

        // check username password with db
        String cypherRecord = customer.getPassword();
        boolean isPasswordMatched = SecurityUtil.isOriginMatchCypher(password, cypherRecord);
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
    public void doLogout(HttpServletRequest request) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();

        // remove cache state from cache
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + userId);
    }

    @Override
    public Customer getUserInfoBasedOnToken(String tokenString) {
        AuthToken authToken = TokenHelper.parseAuthTokenString(tokenString);
        String userId = authToken.getUserId();
        Customer customerBean = customerDao.findOneByBusinessId(userId);
        if (customerBean == null) {
            throw new RequestException(
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getCode());
        }
        // remove sensitive info
        customerBean.setPassword(null);
        return customerBean;
    }

    @Override
    public void doRegisterUser(UserRegisterParam registerParam) {
        String userName = registerParam.getUserName();
        String userId = registerParam.getUserId();

        // check existence
        Customer prevResult = customerDao.findOneByBusinessId(userId);
        if (prevResult != null) {
            throw new RequestException(
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode()
            );
        }

        // encrypt password before store it in db
        String cypher = SecurityUtil.encrypt(registerParam.getPassword());

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setUserName(userName);
        customer.setPassword(cypher);
        customer.setDescription("New User");

        customerDao.insertOne(customer);
    }

    @Override
    public Customer getUserInfoBasedByUserId(String userId) {
        Customer customerBean = customerDao.findOneByBusinessId(userId);
        if (customerBean == null) {
            throw new RequestException(
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
        }
        // remove sensitive info
        customerBean.setPassword(null);
        return customerBean;
    }

    @Override
    public PageBean<Customer> getCustomerByPage(int pageNo, int pageSize) {

        // get total records num, which is important for paging
        int totalRows = customerDao.findTotalCount();

        // init page dto
        PageBean<Customer> pageBean = new PageBean<>(
                pageSize,
                totalRows,
                pageNo
        );

        // get start row for page query
        int start = pageBean.getStartRow();
        List<Customer> customers = customerDao.findAllByPage(start, pageSize);

        // remove sensitive info
        customers.forEach(customer -> customer.setPassword(null));
        pageBean.setBeans(customers);

        return pageBean;
    }

    @Override
    public void doUpdateUserExceptPassword(HttpServletRequest request, Customer customer) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);

        customer.setUserId(authToken.getUserId());
        customerDao.updateOne(customer);
    }

    @Override
    public void doUpdateUserPassword(HttpServletRequest request, String originalPassword, String newPassword) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();

        // get record
        Customer originalCustomerRecord = customerDao.findOneByBusinessId(userId);
        String originalCypher = originalCustomerRecord.getPassword();

        if (!SecurityUtil.isOriginMatchCypher(originalPassword, originalCypher)) {
            throw new RequestException(
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
            );
        }

        // update the password
        originalCustomerRecord.setPassword(SecurityUtil.encrypt(newPassword));
        customerDao.updateOne(originalCustomerRecord);
//        customerDao.updatePasswordOne(userId, SecurityUtil.encrypt(newPassword));
    }
}
