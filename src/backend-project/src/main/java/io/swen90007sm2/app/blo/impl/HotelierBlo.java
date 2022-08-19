package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.SecurityUtil;

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
}
