package io.swen90007sm2.alpheccaboot.core.aop.proxy;

import io.swen90007sm2.alpheccaboot.bean.MethodCalling;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;

/**
 * byte buddy is the replacement of CgLib for high version java
 *
 * @author xiaotian
 */
public class ByteBuddyProxy {

    private static AbstractInterceptor interceptor;
    private static Object targetObj;

//    public ByteBuddyProxy(AbstractInterceptor interceptor, Object targetObj) {
//        ByteBuddyProxy.interceptor = interceptor;
//        ByteBuddyProxy.targetObj = targetObj;
//    }

    @RuntimeType
    public static Object intercept(@This Object self,
                            @Origin Method method,
                            @AllArguments Object[] args,
                            @SuperMethod Method superMethod) throws Throwable {

        MethodCalling methodCalling = new MethodCalling(targetObj, method, args);

        return interceptor.intercept(methodCalling);
    }

    public static synchronized Object enhanceWithProxy (Object targetObj, AbstractInterceptor interceptor) {
        Class<?> targetClass = targetObj.getClass();

        ByteBuddyProxy.interceptor = interceptor;
        ByteBuddyProxy.targetObj = targetObj;

        Class<?> proxyClass = new ByteBuddy()
                .subclass(targetClass)
                .method(
                        ElementMatchers.any()
                ).intercept(MethodDelegation.to(ByteBuddyProxy.class))
                .make()
                .load(targetClass.getClassLoader())
                .getLoaded();

        return ReflectionUtil.genNewInstanceByClass(proxyClass);
    }

}
