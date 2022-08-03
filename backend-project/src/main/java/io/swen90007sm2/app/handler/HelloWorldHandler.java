package io.swen90007sm2.app.handler;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Handler;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IDemoBlo;

@Handler(path = "/hello")
public class HelloWorldHandler {

    @AutoInjected
    IDemoBlo demoBlo;

    @HandlesRequest(path = "", method = RequestMethod.GET)
    public R handleHello(@QueryParam("name") String name) {
        return R.ok().setData(demoBlo.getHelloWorld() + name);
    }
}
