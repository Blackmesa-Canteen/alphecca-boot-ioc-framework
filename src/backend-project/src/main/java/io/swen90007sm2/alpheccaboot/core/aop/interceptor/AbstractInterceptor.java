package io.swen90007sm2.alpheccaboot.core.aop.interceptor;


import io.swen90007sm2.alpheccaboot.bean.MethodCalling;

import java.sql.SQLException;

/**
 * interceptor super class
 *
 * @author xiaotian
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public abstract class AbstractInterceptor {

    // default it is in the head
    private int order = -1;

    /**
     * see whether this interceptor supports this bean
     * @param bean targetBean
     * @return true is interceptor supports the bean
     */
    public boolean supports(Object bean) {
        return false;
    }

    /**
     * override this method to enhance the Methodcalling.
     */
    public abstract Object intercept(MethodCalling methodCalling);

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
