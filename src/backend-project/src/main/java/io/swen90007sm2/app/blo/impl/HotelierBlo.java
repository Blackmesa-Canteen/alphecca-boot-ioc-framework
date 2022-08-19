package io.swen90007sm2.app.blo.impl;


import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class HotelierBlo implements IHotelierBlo {

    @AutoInjected
    IHotelierDao hotelierDao;

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    /**
     * Get JWT token if the loginParam is correct
     *
     * @param loginParam loginParam from controller
     * @return valid token. If null. loginParam is not valid
     */
    @Override
    public AuthToken doLoginAndGenToken(LoginParam loginParam) {
        // get role from db
        String userId = loginParam.getUserId();
        String password = loginParam.getPassword();

        /*
         * Identity Map's cache-Aside implementation
         * <br/>
         * if the data exists in cache, get from cache,
         * if not, get from db.
         * <br/>
         * data in the cache will be expired to guarantee data eventually consistent
         * <br/>
         */
        Hotelier hotelier = getHotelierFromCacheOrDb(userId);

        // prevent double login, check token cache
        // watch out: different Cache prefix
        if (cache.get(CacheConstant.TOKEN_KEY_PREFIX + userId).isPresent()) {
            throw new RequestException(
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getCode()
            );
        }

        // check username password with db
        String cypherRecord = hotelier.getPassword();
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

    /**
     * logout current customer
     *
     * @param request http request
     */
    @Override
    public void doLogout(HttpServletRequest request) {

    }

    /**
     * get userInfo bean from db with token
     *
     * @param tokenString token string
     * @return Customer bean
     */
    @Override
    public Hotelier getUserInfoBasedOnToken(String tokenString) {
        return null;
    }

    /**
     * register a new user
     *
     * @param registerParam param
     */
    @Override
    public void doRegisterUser(UserRegisterParam registerParam) {

    }

    /**
     * get userInfoBean by userId
     *
     * @param userId user id string
     * @return Customer bean
     */
    @Override
    public Hotelier getUserInfoBasedByUserId(String userId) {
        return null;
    }

    /**
     * update user info, except password.
     * will check request token to find target customer to modify on
     *
     * @param request
     * @param param
     */
    @Override
    public void doUpdateUserExceptPassword(HttpServletRequest request, UserUpdateParam param) {

    }

    /**
     * update customer pwd
     *
     * @param request          request
     * @param originalPassword original pwd
     * @param newPassword      new pwd
     */
    @Override
    public void doUpdateUserPassword(HttpServletRequest request, String originalPassword, String newPassword) {

    }

    /**
     * Identity Map's cache-Aside implementation
     * <br/>
     * if the data exists, get from cache,
     * if not, get from db.
     * <br/>
     * data in the cache will be expired to guarantee data eventually consistent
     * <br/>
     * using synchronized to prevent Cache Penetration, guarantee only one thread can update the cache,
     * rather than multiple threads rushed to query database and refresh cache again and again.
     *
     * @param userId customer's userId
     * @return customer object
     */
    private synchronized Hotelier getHotelierFromCacheOrDb(String userId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_KEY_PREFIX + userId);
        Hotelier hotelier = null;
        if (cacheItem.isEmpty()) {
            hotelier = hotelierDao.findOneByBusinessId(userId);
            // if no such customer
            if (hotelier == null) {
                throw new RequestException (
                        StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                        StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
            }

            // use randomed expiration time to prevent Cache Avalanche
            cache.put(
                    CacheConstant.ENTITY_KEY_PREFIX + userId,
                    hotelier,
                    RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                    TimeUnit.MILLISECONDS
            );
        } else {
            hotelier = (Hotelier) cacheItem.get();
        }
        return hotelier;
    }
}
