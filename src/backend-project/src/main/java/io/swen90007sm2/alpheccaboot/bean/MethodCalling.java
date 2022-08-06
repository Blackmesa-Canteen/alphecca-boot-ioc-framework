package io.swen90007sm2.alpheccaboot.bean;

import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * a bean for one Method calling, cache essential elements for a method calling. used in aop and interceptor
 * to cache the original method calling, after enhance logic, can call original method with this bean.
 * @author xiaotian
 */
public class MethodCalling {
    //target object
    private final Object targetObject;
    //target method
    private final Method targetMethod;
    //the parameter of target method
    private final Object[] args;

    public MethodCalling(Object targetObject, Method targetMethod, Object[] args) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.args = args;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    /**
     * continue call the method
     */
    public Object proceed() {
        return ReflectionUtil.invokeMethod(targetObject, targetMethod, args);
    }
}
