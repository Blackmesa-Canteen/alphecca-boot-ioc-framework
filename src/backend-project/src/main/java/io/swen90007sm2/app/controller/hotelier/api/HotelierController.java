package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.security.bean.AuthToken;

import javax.validation.Valid;

@Controller(path = "/api/hotelier")
//@Validated
public class HotelierController {

    @AutoInjected
    IHotelierBlo hotelierBlo;

    @HandlesRequest(path = "/login", method = RequestMethod.POST)
    public R login(@RequestJsonBody @Valid LoginParam loginParam) {

        AuthToken authToken = hotelierBlo.doLoginAndGenToken(loginParam);

        return R.ok().setData(authToken);
    }

}
