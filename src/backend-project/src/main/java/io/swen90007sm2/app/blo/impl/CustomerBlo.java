package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
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

        /*
         * Identity Map's cache-Aside implementation
         * <br/>
         * if the data exists, get from cache,
         * if not, get from db.
         * <br/>
         * data in the cache will be expired to guarantee data eventually consistent
         */
        Customer customer = getCustomerFromCacheOrDb(userId);

        // prevent double login, check token cache
        // watch out: different Cache prefix
        if (cache.get(CacheConstant.TOKEN_KEY_PREFIX + userId).isPresent()) {
            throw new RequestException(
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getCode()
            );
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

        // store the token into token cache with expiration time
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

        // remove cache state from token cache to logout
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
    }

    @Override
    public Customer getUserInfoBasedOnToken(String tokenString) {
        AuthToken authToken = TokenHelper.parseAuthTokenString(tokenString);
        String userId = authToken.getUserId();

        // check token validity
        checkTokenValidity(authToken);

        // cache result bean as the Identity map
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_KEY_PREFIX + userId);
        Customer customerBean = null;
        if (cacheItem.isEmpty()) {
            customerBean = customerDao.findOneByBusinessId(userId);
            if (customerBean == null) {
                throw new RequestException(
                        StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                        StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
            }

            cache.put(
                    CacheConstant.ENTITY_KEY_PREFIX + userId,
                    customerBean,
                    CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX,
                    TimeUnit.MILLISECONDS
            );
        } else {
            customerBean = (Customer) cacheItem.get();
        }

        // need to copy bean, because we need to remove sensitive data for showing,
        // without affecting the database record in cache
        Customer res = new Customer();
        BeanUtil.copyProperties(customerBean, res);
        // remove sensitive info
        res.setPassword(CommonConstant.NULL);
        return res;
    }

    @Override
    public void doRegisterUser(UserRegisterParam registerParam) {
        String userName = registerParam.getUserName();
        String userId = registerParam.getUserId();

        // check existence
        // will not use cache to prevent inconsistent data
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
        customer.setId(RandomStringUtils.randomAlphanumeric(32));
        customer.setUserId(userId);
        customer.setUserName(userName);
        customer.setPassword(cypher);
        customer.setDescription("New User");
        // TODO user unit of work helper
        customerDao.insertOne(customer);
    }

    @Override
    public Customer getUserInfoBasedByUserId(String userId) {

        // cache result bean as the Identity map
        // use random expiration time to prevent Cache avalanche
        Customer customerBean = getCustomerFromCacheOrDb(userId);

        // need to copy bean, because we need to remove sensitive data for showing,
        // without affecting the database record in cache
        Customer res = new Customer();
        BeanUtil.copyProperties(customerBean, res);
        // remove sensitive info
        res.setPassword(CommonConstant.NULL);
        return res;
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
        // use result from db to update the cache
        customers.forEach(customer -> {
            if (customer != null) {
                cache.put(
                        CacheConstant.ENTITY_KEY_PREFIX + customer.getUserId(),
                        customer,
                        RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                        TimeUnit.MILLISECONDS
                );
            }
        });

        // remove sensitive info,
        // need to copy to prevent impact on cached data
        List<Customer> res = BeanUtil.copyToList(customers, Customer.class);
        res.forEach(customer -> customer.setPassword(CommonConstant.NULL));
        pageBean.setBeans(res);

        return pageBean;
    }

    @Override
    public void doUpdateUserExceptPassword(HttpServletRequest request, UserUpdateParam param) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        // get record
        // cache result bean as the Identity map
        // use random expiration time to prevent Cache avalanche
        Customer customerBean = getCustomerFromCacheOrDb(userId);
        // set new value
        customerBean.setDescription(param.getDescription());
        customerBean.setUserName(param.getUserName());
        customerBean.setAvatarUrl(param.getAvatarUrl());

        // TODO unit of work helper
        customerDao.updateOne(customerBean);

        // cache destroy MUST be after the database updating
        cache.remove(CacheConstant.ENTITY_KEY_PREFIX + userId);
    }

    @Override
    public void doUpdateUserPassword(HttpServletRequest request, String originalPassword, String newPassword) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();

        // get record
        // cache result bean as the Identity map
        // use random expiration time to prevent Cache avalanche
        Customer customerBean = getCustomerFromCacheOrDb(userId);

        String originalCypher = customerBean.getPassword();

        if (!SecurityUtil.isOriginMatchCypher(originalPassword, originalCypher)) {
            throw new RequestException(
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
            );
        }

        // update the password
        customerBean.setPassword(SecurityUtil.encrypt(newPassword));
        // TODO unit of work
        customerDao.updateOne(customerBean);

        // cache destroy MUST be after the database updating
        cache.remove(CacheConstant.ENTITY_KEY_PREFIX + userId);

        // logout
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
    }

    private void checkTokenValidity(AuthToken authToken) {
        String key = CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId();
        Optional<Object> tokenRecord = cache.get(key);
        if(tokenRecord.isEmpty()) {
            // Don't have corresponding token in server cache, it could be: Not login or login expired
            throw new RequestException(
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getCode()
            );
        } else {
            // check token correctness
            AuthToken tokenBeanInCache = (AuthToken)tokenRecord.get();
            if (!authToken.getToken().equals(tokenBeanInCache.getToken())) {
                throw new RequestException(
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
                );
            }
        }
    }

    /**
     * Identity Map's cache-Aside implementation
     * <br/>
     * if the data exists, get from cache,
     * if not, get from db.
     * <br/>
     * data in the cache will be expired to guarantee data eventually consistent
     * @param userId customer's userId
     * @return customer object
     */
    private Customer getCustomerFromCacheOrDb(String userId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_KEY_PREFIX + userId);
        Customer customer = null;
        if (cacheItem.isEmpty()) {
            customer = customerDao.findOneByBusinessId(userId);
            // if no such customer
            if (customer == null) {
                throw new RequestException (
                        StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                        StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
            }

            // use randomed expiration time to prevent Cache Avalanche
            cache.put(
                    CacheConstant.ENTITY_KEY_PREFIX + userId,
                    customer,
                    RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                    TimeUnit.MILLISECONDS
            );
        } else {
            customer = (Customer) cacheItem.get();
        }
        return customer;
    }
}
