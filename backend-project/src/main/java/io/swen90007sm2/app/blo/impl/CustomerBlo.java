package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.model.param.LoginParam;

@Blo
public class CustomerBlo implements ICustomerBlo {

    @Override
    public String getTokenByLoginParam(LoginParam loginParam) throws Exception {

        // check username password with db

        // get role from db

        // build token by TokenHelper, then return token
        return null;
    }
}