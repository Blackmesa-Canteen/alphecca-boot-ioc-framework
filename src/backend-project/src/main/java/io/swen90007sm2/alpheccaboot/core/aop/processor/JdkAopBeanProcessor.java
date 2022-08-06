package io.swen90007sm2.alpheccaboot.core.aop.processor;

import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import io.swen90007sm2.alpheccaboot.core.aop.proxy.JdkProxy;

/**
 * jdk enhancer
 *
 * @author xiaotian
 */
public class JdkAopBeanProcessor extends AbstractAopBeanProcessor{

    @Override
    public Object enhanceBean(Object bean, AbstractInterceptor interceptor) {
        return JdkProxy.enhanceWithProxy(bean, interceptor);
    }
}
