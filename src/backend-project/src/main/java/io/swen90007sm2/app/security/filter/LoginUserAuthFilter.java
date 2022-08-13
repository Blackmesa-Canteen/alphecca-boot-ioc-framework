package io.swen90007sm2.app.security.filter;

import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.util.Assert;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;

import java.util.Optional;

/**
 * @author xiaotian
 * @description authentication filter for all kinds of login user. reject if the user is not login.
 * @create 2022-08-03 20:20
 */
@Filter(name = SecurityConstant.LOGIN_USER)
public class LoginUserAuthFilter extends AbstractAuthFilter{

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cacheStorage;

    @Override
    boolean performRoleAuthorization(AuthToken tokenBean) throws RequestException {
        Filter filterAnno = this.getClass().getAnnotation(Filter.class);
        Assert.notNull(filterAnno, "Filter annotation of the AuthFilter should not be null");

        // check cache to compare token
        String key = CacheConstant.TOKEN_KEY_PREFIX + tokenBean.getUserId();
        Optional<Object> tokenRecord = cacheStorage.get(key);
        if(tokenRecord.isEmpty()) {
            // Don't have corresponding token in server cache, it could be: Not login or login expired
            throw new RequestException(
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getCode()
            );
        } else {
            // check token correctness
            AuthToken tokenBeanInCache = (AuthToken)tokenRecord.get();
            if (!tokenBean.getToken().equals(tokenBeanInCache.getToken())) {
                throw new RequestException(
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
                );
            }
        }

        return true;
    }
}
