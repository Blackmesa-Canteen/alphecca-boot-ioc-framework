package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.PasswordUpdateParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller(path = "/api/hotelier")
@Validated
public class HotelierController {
    @AutoInjected
    IHotelierBlo hotelierBlo;

    /**
     * login
     *
     * @param loginParam request json body
     * @return R with token
     */
    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) {

        AuthToken authToken = hotelierBlo.doLoginAndGenToken(loginParam);

        return R.ok().setData(authToken);
    }

    /**
     * update password for current login user
     * @return ok if success
     */
    @HandlesRequest(path = "/login", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R changeUserPassword(HttpServletRequest request, @RequestJsonBody @Valid PasswordUpdateParam param) {
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        hotelierBlo.doUpdateUserPassword(request,
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
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R logout(HttpServletRequest request) {
        hotelierBlo.doLogout(request);

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
        hotelierBlo.doRegisterUser(userRegisterParam);
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
        Hotelier hotelierBean;
        if (StringUtils.isEmpty(userId)) {
            hotelierBean = hotelierBlo.getUserInfoBasedOnToken(
                    request.getHeader(SecurityConstant.JWT_HEADER_NAME)
            );
        } else {
            hotelierBean = hotelierBlo.getUserInfoBasedByUserId(userId);
        }

        return R.ok().setData(hotelierBean);
    }

    /**
     * update userInfo.
     *
     * @param request         request
     * @param userUpdateParam json param
     * @return ok if success
     */
    @HandlesRequest(path = "/", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R updateUserInfo(HttpServletRequest request, @RequestJsonBody @Valid UserUpdateParam userUpdateParam) {
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        hotelierBlo.doUpdateUserExceptPassword(request, userUpdateParam);
        UnitOfWorkHelper.getCurrent().commit();
        return R.ok();
    }

}
