package io.swen90007sm2.alpheccaboot.core.aop.processor;

import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import io.swen90007sm2.alpheccaboot.core.aop.proxy.CglibProxy;

/**
 * cglib enhancer
 *
 * !!! Deprecated !!!
 *
 * now Cglib is replaced by ByteBuddy proxy,
 * because CgLib stopped maintain in 2019 and can not support the latest JDK.
 *
 * @author xiaotian
 */
@Deprecated
public class CgLibAopBeanProcessor extends AbstractAopBeanProcessor {
    @Override
    public Object enhanceBean(Object bean, AbstractInterceptor interceptor) {
        return CglibProxy.enhanceWithProxy(bean, interceptor);
    }
}
