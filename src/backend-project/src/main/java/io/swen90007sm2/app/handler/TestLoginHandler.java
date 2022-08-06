package io.swen90007sm2.app.handler;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Handler;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.PathVariable;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Handler(path = "/test_user")
public class TestLoginHandler {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    @HandlesRequest(path = "/login/{roleNum}", method = RequestMethod.GET)
    public R putTokenAs(@PathVariable(value = "roleNum") int num) {
        String userId = "asd";
        String token = "";
        if (num == 0) {
            token = TokenHelper.genAuthToken(userId, AuthRole.CUSTOMER_ROLE);
        } else if (num == 1) {
            token = TokenHelper.genAuthToken(userId, AuthRole.ADMIN_ROLE);
        } else if (num == 2) {
            token = TokenHelper.genAuthToken(userId, AuthRole.HOTELIER_ROLE);
        }

        cache.put(CacheConstant.TOKEN_KEY_PREFIX + userId, token, SecurityConstant.DEFAULT_TOKEN_EXPIRATION_TIME_MS, TimeUnit.MILLISECONDS);
        return R.ok().setData(token);
    }

    @HandlesRequest(path = "/logout", method = RequestMethod.GET)
    public R logout() {
        String userId = "asd";

        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + userId);
        return R.ok();
    }

    @HandlesRequest(path = "/profile", method = RequestMethod.GET)
    public R getTokenInServer(HttpServletRequest request, @QueryParam(value = "name") String name) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        Optional<Object> resObj = cache.get(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
        if (resObj.isPresent()) {
            return R.ok().setData(authToken);
        } else {
            return R.error().setData("Not login (from handler)");
        }
    }

    @HandlesRequest(path = "/customer", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R needCustomer() {
        return R.ok();
    }

    @HandlesRequest(path = "/admin", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R needAdmin() {
        return R.ok();
    }

    @HandlesRequest(path = "/hotel", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R needHotelier() {
        return R.ok();
    }
}
