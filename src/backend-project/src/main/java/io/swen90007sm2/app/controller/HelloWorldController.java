package io.swen90007sm2.app.controller;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.PathVariable;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;

@Controller(path = "/hello")
public class HelloWorldController {

    @HandlesRequest(path = "/test", method = RequestMethod.GET)
    public R handleHello() {
        return R.ok().setData("no param");
    }

    @HandlesRequest(path = "/test/{id}", method = RequestMethod.GET)
    public R handleHello(@PathVariable(value = "id") String id) {
        return R.ok().setData(id);
    }


}
