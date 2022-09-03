package io.swen90007sm2.app.controller.admin.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.impl.CustomerBlo;
import io.swen90007sm2.app.blo.IAdminBlo;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.util.List;

@Controller(path = "/api/admin")
public class AdminController {

    @AutoInjected
    IAdminBlo adminBlo;

    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) {
        AuthToken authToken = adminBlo.doLoginAndGenToken(loginParam);
        return R.ok().setData(authToken);
    }

//    @HandlesRequest(path = "/logout", method = RequestMethod.POST)
//    public R logout() {
//        adminBlo.doLogout(null);
//        return R.ok();
//    }
//    @HandlesRequest(path = "/", method = RequestMethod.POST)
//    public R register(@RequestJsonBody @Valid UserRegisterParam userRegisterParam) {
//        adminBlo.doRegisterUser(userRegisterParam);
//        return R.ok();
//    }

    @HandlesRequest(path = "/logout", method = RequestMethod.GET)
    @AppliesFilter(filterNames = SecurityConstant.ADMIN_ROLE_NAME)
    public R logout(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        adminBlo.doLogout(authToken);
        return R.ok();
    }



}
