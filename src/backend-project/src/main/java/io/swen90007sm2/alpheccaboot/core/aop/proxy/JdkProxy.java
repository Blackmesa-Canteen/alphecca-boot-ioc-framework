package io.swen90007sm2.alpheccaboot.core.aop.proxy;

import io.swen90007sm2.alpheccaboot.bean.MethodCalling;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jak proxy is used when object uses a Interface, otherwise, use CGLib to enhance byte code
 *
 * @author xiaotian
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public class JdkProxy implements InvocationHandler {

    private final AbstractInterceptor interceptor;
    private final Object targetObj;

    public JdkProxy(AbstractInterceptor interceptor, Object targetObj) {
        this.interceptor = interceptor;
        this.targetObj = targetObj;
    }

    /**
     * Processes a method invocation on a proxy instance and returns the result.
     * This method will be invoked on an invocation handler when a method is
     * invoked on a proxy instance that it is associated with
     *
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // original method calling, cache it into bean
        MethodCalling methodCalling = new MethodCalling(targetObj, method, args);

        // enhance the original method calling with interceptor logic
        return interceptor.intercept(methodCalling);
    }

    /**
     * wrap original object with proxy instance
     */
    public static Object enhanceWithProxy(Object targetObj, AbstractInterceptor interceptor) {
        JdkProxy proxy = new JdkProxy(interceptor, targetObj);

        return Proxy.newProxyInstance(
                targetObj.getClass().getClassLoader(),
                targetObj.getClass().getInterfaces(),
                proxy
        );
    }


}
