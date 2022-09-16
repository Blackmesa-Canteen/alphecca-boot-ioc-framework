package io.swen90007sm2.alpheccaboot.core.aop.proxy;

import io.swen90007sm2.alpheccaboot.bean.MethodCalling;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Ref;

/**
 * byte buddy is the replacement of CgLib for high version java
 *
 * TODO Not supporting chain proxy
 * TODO Object ERROR in Debug mode!
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
    public static Object intercept(@This Object proxy,
                                   @Origin Method method,
                                   @AllArguments Object[] args,
                                   @SuperMethod Method superMethod) throws Throwable {

        // TODO problem: not supporting chain proxy
        MethodCalling methodCalling = new MethodCalling(targetObj, method, args);
        return interceptor.intercept(methodCalling);
    }

    public static synchronized Object enhanceWithProxy(Object targetObj, AbstractInterceptor interceptor) {
        Class<?> targetClass = targetObj.getClass();
        Class<?> proxyClass = targetClass;

        ByteBuddyProxy.interceptor = interceptor;
        ByteBuddyProxy.targetObj = targetObj;

        if (targetObj.getClass().getName().contains("$ByteBuddy$")) {
            proxyClass = targetClass.getSuperclass();
        }

        try (DynamicType.Unloaded<?> make = new ByteBuddy()
                .subclass(proxyClass)
                .method(
                        ElementMatchers.any()
                ).intercept(MethodDelegation.to(ByteBuddyProxy.class))
                .make()) {

            Class<?> enhancer = make.load(targetClass.getClassLoader()).getLoaded();

            // TODO fix this!!
            return ReflectionUtil.genNewInstanceByClass(enhancer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
