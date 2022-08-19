package io.swen90007sm2.app.blo.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.db.constant.DbConstant;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;
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
        String token = TokenHelper.genAuthToken(userId, AuthRole.HOTELIER_ROLE);

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
     * logout current hotelier
     *
     * @param request http request
     */
    @Override
    public void doLogout(HttpServletRequest request) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);

        // remove cache state from token cache to logout
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
    }

    /**
     * get userInfo bean from db with token
     *
     * @param tokenString token string
     * @return Hotelier bean
     */
    @Override
    public Hotelier getUserInfoBasedOnToken(String tokenString) {
        AuthToken authToken = TokenHelper.parseAuthTokenString(tokenString);
        String userId = authToken.getUserId();

        // check token validity
        checkTokenValidity(authToken);

        // get cache result bean as the Identity map
        Hotelier hotelierBean = getHotelierFromCacheOrDb(userId);

        // need to copy bean, because we need to remove sensitive data for showing,
        // without affecting the database record in cache
        Hotelier res = new Hotelier();
        BeanUtil.copyProperties(hotelierBean, res);
        // remove sensitive info
        res.setPassword(CommonConstant.NULL);
        return res;
    }

    /**
     * register a new user
     *
     * @param registerParam param
     */
    @Override
    public void doRegisterUser(UserRegisterParam registerParam) {
        String userName = registerParam.getUserName();
        String userId = registerParam.getUserId();

        // check existence
        // will not use cache to prevent inconsistent data
        Hotelier prevResult = hotelierDao.findOneByBusinessId(userId);

        if (prevResult != null) {
            throw new RequestException(
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getCode()
            );
        }

        // encrypt password before store it in db
        String cypher = SecurityUtil.encrypt(registerParam.getPassword());

        Hotelier hotelier = new Hotelier();
        hotelier.setId(RandomStringUtils.randomAlphanumeric(DbConstant.PRIMARY_KEY_LENGTH));
        hotelier.setUserId(userId);
        hotelier.setUserName(userName);
        hotelier.setPassword(cypher);
        hotelier.setDescription("New User");
        // TODO user unit of work helper
        UnitOfWorkHelper current = UnitOfWorkHelper.getCurrent();
        current.registerNew(hotelier, hotelierDao);
    }

    /**
     * get userInfoBean by userId
     *
     * @param userId user id string
     * @return Hotelier bean
     */
    @Override
    public Hotelier getUserInfoBasedByUserId(String userId) {
        // cache result bean as the Identity map
        // use random expiration time to prevent Cache avalanche
        Hotelier hotelierBean = getHotelierFromCacheOrDb(userId);

        // need to copy bean, because we need to remove sensitive data for showing,
        // without affecting the database record in cache
        Hotelier res = new Hotelier();
        BeanUtil.copyProperties(hotelierBean, res);
        // remove sensitive info
        res.setPassword(CommonConstant.NULL);
        return res;
    }

    /**
     * update user info, except password.
     * will check request token to find target hotelier to modify on
     *
     * @param request
     * @param param
     */
    @Override
    public void doUpdateUserExceptPassword(HttpServletRequest request, UserUpdateParam param) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        // get record
        // cache result bean as the Identity map
        // use random expiration time to prevent Cache avalanche
        Hotelier hotelierBean = getHotelierFromCacheOrDb(userId);
        // set new value
        hotelierBean.setDescription(param.getDescription());
        hotelierBean.setUserName(param.getUserName());
        hotelierBean.setAvatarUrl(param.getAvatarUrl());

        // unit of work helper
        UnitOfWorkHelper.getCurrent().registerDirty(hotelierBean, hotelierDao, userId);
    }

    /**
     * update hotelier pwd
     *
     * @param request          request
     * @param originalPassword original pwd
     * @param newPassword      new pwd
     */
    @Override
    public void doUpdateUserPassword(HttpServletRequest request, String originalPassword, String newPassword) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();

        // get record
        // cache result bean as the Identity map
        // use random expiration time to prevent Cache avalanche
        Hotelier hotelierBean = getHotelierFromCacheOrDb(userId);

        String originalCypher = hotelierBean.getPassword();

        if (!SecurityUtil.isOriginMatchCypher(originalPassword, originalCypher)) {
            throw new RequestException(
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
            );
        }

        // update the password
        hotelierBean.setPassword(SecurityUtil.encrypt(newPassword));
        // unit of work
        UnitOfWorkHelper.getCurrent().registerDirty(hotelierBean, hotelierDao, userId);
//        hotelierDao.updateOne(hotelierBean);
        // cache destroy MUST be after the database updating
//        cache.remove(CacheConstant.ENTITY_KEY_PREFIX + userId);

        // logout, clear up login state
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
    }

    /**
     * check whether the incoming token is valid for current user, or not
     * @param authToken incoming token from header
     */
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
     * <br/>
     * using synchronized to prevent Cache Penetration, guarantee only one thread can update the cache,
     * rather than multiple threads rushed to query database and refresh cache again and again.
     *
     * @param userId hotelier's userId
     * @return hotelier object
     */
    private synchronized Hotelier getHotelierFromCacheOrDb(String userId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_KEY_PREFIX + userId);
        Hotelier hotelier = null;
        if (cacheItem.isEmpty()) {
            hotelier = hotelierDao.findOneByBusinessId(userId);
            // if no such hotelier
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
