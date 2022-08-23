package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.db.constant.DbConstant;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Blo
public class HotelierBlo implements IHotelierBlo {
    @AutoInjected
    IHotelierDao hotelierDao;

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

    @Override
    public void doRegisterUser(UserRegisterParam userRegisterParam) {
        String userName = userRegisterParam.getUserName();
        String userId = userRegisterParam.getUserId();

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
        String cypher = SecurityUtil.encrypt(userRegisterParam.getPassword());

        Hotelier hotelier = new Hotelier();
        hotelier.setId(RandomStringUtils.randomAlphanumeric(DbConstant.PRIMARY_KEY_LENGTH));
        hotelier.setUserId(userId);
        hotelier.setUserName(userName);
        hotelier.setPassword(cypher);
        hotelier.setDescription("New User");


        UnitOfWorkHelper current = UnitOfWorkHelper.getCurrent();
        current.registerNew(hotelier, hotelierDao);
    }

    @Override
    public void doLogout(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        System.out.println(authToken.getUserId());
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());

    }


    @Override
    public void doUpdateUserPassword(HttpServletRequest request, String originalPassword, String newPassword) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();

        Hotelier hotelierBean = getHotelierFromCacheOrDb(userId);

        String originalCypher = hotelierBean.getPassword();

        if (!SecurityUtil.isOriginMatchCypher(originalPassword, originalCypher)) {
            throw new RequestException(
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
            );
        }
        hotelierBean.setPassword(SecurityUtil.encrypt(newPassword));
        UnitOfWorkHelper.getCurrent().registerDirty(hotelierBean, hotelierDao, CacheConstant.ENTITY_KEY_PREFIX + userId);
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
    }

    @Override
    public Hotelier getHotelierInfoByToken(String header) {
        AuthToken authToken = TokenHelper.parseAuthTokenString(header);
        String userId = authToken.getUserId();
        checkTokenValidity(authToken);
        Hotelier hotelierBean = getHotelierFromCacheOrDb(userId);
        Hotelier res = new Hotelier();
        // Return the hotelier info without password.
        BeanUtil.copyProperties(hotelierBean, res);
        res.setPassword(CommonConstant.NULL);
        return res;
    }

    @Override
    public Hotelier getHotelierInfoByUserId(String userId) {
        Hotelier hotelierBean = getHotelierFromCacheOrDb(userId);
        Hotelier res = new Hotelier();
        // Return the hotelier info without password.
        BeanUtil.copyProperties(hotelierBean, res);
        res.setPassword(CommonConstant.NULL);
        return res;
    }

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
        return hotelier;    }

    private void checkTokenValidity(AuthToken authToken) {
        String key = CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId();
        Optional<Object> tokenRecord = cache.get(key);
        if (tokenRecord.isEmpty()) {
            throw new RequestException(
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getCode()
            );
        } else {
            AuthToken tokenBeanInCache = (AuthToken)tokenRecord.get();
            if (!authToken.getToken().equals(tokenBeanInCache.getToken())) {
                throw new RequestException(
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
                );
            }
        }
    }
}
