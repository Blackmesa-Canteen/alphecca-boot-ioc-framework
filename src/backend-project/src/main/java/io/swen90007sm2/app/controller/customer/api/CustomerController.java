package io.swen90007sm2.app.controller.customer.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.*;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller(path = "/api/customer")
@Validated
public class CustomerController {

    @AutoInjected
    ICustomerBlo customerBlo;

    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) throws Exception {

        AuthToken authToken = customerBlo.doLoginAndGenToken(loginParam);

        return R.ok().setData(authToken);
    }

    @HandlesRequest(path = "/register", method = RequestMethod.POST)
    public R register(@RequestJsonBody @Valid UserRegisterParam userRegisterParam) {
        customerBlo.doRegisterUser(userRegisterParam);
        return R.ok();
    }

    @HandlesRequest(path = "/", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R getUserInfo(HttpServletRequest request) {

        Customer customerBean = customerBlo.getUserInfoBasedOnToken(
                request.getHeader(SecurityConstant.JWT_HEADER_NAME)
        );

        return R.ok().setData(customerBean);
    }

}
