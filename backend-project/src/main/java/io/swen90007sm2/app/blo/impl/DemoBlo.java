package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.app.blo.IDemoBlo;
import io.swen90007sm2.app.dao.IDemoDao;

@Blo
public class DemoBlo implements IDemoBlo {

    @Override
    public String getHelloWorld() {
        return "Hello world";
    }
}
