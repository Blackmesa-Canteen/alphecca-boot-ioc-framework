package io.swen90007sm2.app.handler.customer.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Handler;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.model.param.LoginParam;

import javax.validation.Valid;

@Handler(path = "/api/customer")
@Validated
public class CustomerHandler {

    @AutoInjected
    ICustomerBlo customerBlo;

    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            throw new RequestException(
                    StatusCodeEnume.LOGIN_PASSWORD_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_PASSWORD_EXCEPTION.getCode()
                    );
        }

        return R.ok();
    }
}
