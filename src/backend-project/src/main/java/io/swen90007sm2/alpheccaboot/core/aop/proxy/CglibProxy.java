package io.swen90007sm2.alpheccaboot.core.aop.proxy;

import io.swen90007sm2.alpheccaboot.bean.MethodCalling;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib proxy without Interface support, use byte code of java.
 *
 * now Cglib is replaced by ByteBuddy proxy,
 * because CgLib stopped maintain in 2019 and can not support the latest JDK.
 *
 * @author xiaotian
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public class CglibProxy implements MethodInterceptor {

    private final AbstractInterceptor interceptor;
    private final Object targetObj;

    public CglibProxy(AbstractInterceptor interceptor, Object targetObj) {
        this.interceptor = interceptor;
        this.targetObj = targetObj;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) {

        // cache original method calling
        MethodCalling methodCalling = new MethodCalling(targetObj, method, args);

        // enhance the original method calling
        return interceptor.intercept(methodCalling);
    }

    /**
     * enhance a target obj with interceptor and returns the proxy
     */
    public static Object enhanceWithProxy(Object targetObj, AbstractInterceptor interceptor) {
        Class<?> targetClass = targetObj.getClass();
        Class<?> proxyClass = targetClass;

        // to achieve enhancing a Proxy with another Proxy
        if (targetObj.getClass().getName().contains("$$")) {
            proxyClass = targetClass.getSuperclass();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(targetObj.getClass().getClassLoader());
        enhancer.setSuperclass(proxyClass);
        // callback can cal intercept method to enhance the targetObject method
        enhancer.setCallback(new CglibProxy(interceptor, targetObj));
        return enhancer.create();
    }
}
