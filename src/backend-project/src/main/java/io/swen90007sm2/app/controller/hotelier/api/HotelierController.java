package io.swen90007sm2.app.controller.hotelier.api;

import cn.hutool.core.bean.BeanUtil;
import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.PasswordUpdateParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller(path = "/api/hotelier")
//@Validated
public class HotelierController {

    @AutoInjected
    IHotelierBlo hotelierBlo;

    @HandlesRequest(path = "/", method = RequestMethod.POST)
    public R register(@RequestJsonBody @Valid UserRegisterParam userRegisterParam) {
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        hotelierBlo.doRegisterUser(userRegisterParam);
        UnitOfWorkHelper.getCurrent().commit();
        return R.ok();
    }

    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) {
        System.out.println("login");
        AuthToken authToken = hotelierBlo.doLoginAndGenToken(loginParam);

        return R.ok().setData(authToken);
    }

    @HandlesRequest(path = "/logout", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R logout(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);

        hotelierBlo.doLogout(authToken);
        return R.ok();
    }

    @HandlesRequest(path = "/login", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R changeUserPassword(HttpServletRequest request, @RequestJsonBody @Valid PasswordUpdateParam param) {
        // get current user id
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();

        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        hotelierBlo.doUpdateUserPassword(userId,
                param.getOriginalPassword(),
                param.getNewPassword()
        );
        UnitOfWorkHelper.getCurrent().commit();

        return R.ok();
    }

    @HandlesRequest(path = "/", method = RequestMethod.GET)
    public R getUserInfoWithUserId(HttpServletRequest request, @QueryParam(value = "userId") String userId) {
        Hotelier hotelierBean;
        if (StringUtils.isEmpty(userId)) {
            System.out.println("get by token");
            hotelierBean = hotelierBlo.getHotelierInfoByToken(request.getHeader(SecurityConstant.JWT_HEADER_NAME));
        } else {
            System.out.println("get by userId");
            hotelierBean = hotelierBlo.getHotelierInfoByUserId(userId);
        }

        Hotelier res = new Hotelier();
        // Return the hotelier info without password.
        BeanUtil.copyProperties(hotelierBean, res);
        res.setPassword(CommonConstant.NULL);
        return R.ok().setData(res);
    }

}
