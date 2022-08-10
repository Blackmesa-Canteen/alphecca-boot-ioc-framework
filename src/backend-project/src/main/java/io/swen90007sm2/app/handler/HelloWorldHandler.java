package io.swen90007sm2.app.handler;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Handler;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.PathVariable;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IDemoBlo;

@Handler(path = "/hello")
public class HelloWorldHandler {

    @HandlesRequest(path = "/test", method = RequestMethod.GET)
    public R handleHello() {
        return R.ok().setData("no param");
    }

    @HandlesRequest(path = "/test/{id}", method = RequestMethod.GET)
    public R handleHello(@PathVariable(value = "id") String id) {
        return R.ok().setData(id);
    }


}
