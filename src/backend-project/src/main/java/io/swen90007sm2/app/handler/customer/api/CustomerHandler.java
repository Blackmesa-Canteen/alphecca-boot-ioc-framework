package io.swen90007sm2.app.handler.customer.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.*;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.security.bean.AuthToken;

import javax.validation.Valid;

@Handler(path = "/api/customer")
@Validated
public class CustomerHandler {

    @AutoInjected
    ICustomerBlo customerBlo;

    @AutoInjected
    ICustomerDao customerDao;

    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) throws Exception {

        AuthToken authToken = customerBlo.doLoginAndGenToken(loginParam);

        return R.ok().setData(authToken);
    }

    @HandlesRequest(path = "/test", method = RequestMethod.GET)
    public R testGetCustomerById(@QueryParam("userId") String userId) {
        Customer customerBean = customerDao.findCustomerByUserId(userId);

        return R.ok().setData(customerBean);
    }

    @HandlesRequest(path = "/test", method = RequestMethod.POST)
    public R testPostNewCustomer(@RequestJsonBody @Valid Customer customer) {
        int i = customerDao.addNewCustomer(customer);

        if (i == 1) {
            return R.ok();
        } else {
            return R.error(StatusCodeEnume.USER_EXIST_EXCEPTION.getCode(), StatusCodeEnume.USER_EXIST_EXCEPTION.getMessage());
        }
    }
}
