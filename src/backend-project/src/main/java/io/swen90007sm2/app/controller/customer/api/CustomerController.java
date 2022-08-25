package io.swen90007sm2.app.controller.customer.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.*;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.PasswordUpdateParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller(path = "/api/customer")
@Validated
public class CustomerController {

    @AutoInjected
    ICustomerBlo customerBlo;

    /**
     * login
     *
     * @param loginParam request json body
     * @return R with token
     */
    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) {

        AuthToken authToken = customerBlo.doLoginAndGenToken(loginParam);

        return R.ok().setData(authToken);
    }

    /**
     * update password for current login user
     * @return ok if success
     */
    @HandlesRequest(path = "/login", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R changeUserPassword(HttpServletRequest request, @RequestJsonBody @Valid PasswordUpdateParam param) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        customerBlo.doUpdateUserPassword(userId,
                param.getOriginalPassword(),
                param.getNewPassword()
        );
        UnitOfWorkHelper.getCurrent().commit();

        return R.ok();
    }

    /**
     * handles logout
     * @param request httpresuqst
     * @return ok if success
     */
    @HandlesRequest(path = "/logout", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R logout(HttpServletRequest request) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        customerBlo.doLogout(authToken);

        return R.ok();
    }

    /**
     * register
     *
     * @param userRegisterParam request json body
     * @return ok if successful
     */
    @HandlesRequest(path = "/", method = RequestMethod.POST)
    public R register(@RequestJsonBody @Valid UserRegisterParam userRegisterParam) {
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        customerBlo.doRegisterUser(userRegisterParam);
        UnitOfWorkHelper.getCurrent().commit();
        return R.ok();
    }

    /**
     * get user's info based on user Id
     * If the query param is null, get current login user info
     *
     * @param request request
     * @param userId  userId String
     * @return userInfo
     */
    @HandlesRequest(path = "/", method = RequestMethod.GET)
    public R getUserInfoWithUserId(HttpServletRequest request, @QueryParam(value = "userId") String userId) {
        Customer customerBean;
        if (StringUtils.isEmpty(userId)) {
            customerBean = customerBlo.getUserInfoBasedOnToken(
                    request.getHeader(SecurityConstant.JWT_HEADER_NAME)
            );
        } else {
            customerBean = customerBlo.getUserInfoBasedByUserId(userId);
        }

        return R.ok().setData(customerBean);
    }

    /**
     * update userInfo.
     *
     * @param request         request
     * @param userUpdateParam json param
     * @return ok if success
     */
    @HandlesRequest(path = "/", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R updateUserInfo(HttpServletRequest request, @RequestJsonBody @Valid UserUpdateParam userUpdateParam) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        customerBlo.doUpdateUserExceptPassword(userId, userUpdateParam);
        UnitOfWorkHelper.getCurrent().commit();
        return R.ok();
    }

}
