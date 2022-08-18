package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;

/**
 * @author 996Worker
 * @description main controller
 * @create 2022-08-18 23:15
 */
@Controller(path = "/")
public class MainController {


    @HandlesRequest(path = "/", method = RequestMethod.GET)
    public R welcome() {
        return R.ok().setData("Hello, This is alphecca hotel booking backend api server");
    }
}