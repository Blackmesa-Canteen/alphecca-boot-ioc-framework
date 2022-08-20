package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.PasswordUpdateParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;

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
        System.out.println("logout");
        hotelierBlo.doLogout(request);
        return R.ok();
    }

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

}
