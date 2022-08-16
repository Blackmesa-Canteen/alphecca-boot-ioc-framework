package io.swen90007sm2.alpheccaboot.core.aop.interceptor.aop;

import io.swen90007sm2.alpheccaboot.annotation.aop.After;
import io.swen90007sm2.alpheccaboot.annotation.aop.Before;
import io.swen90007sm2.alpheccaboot.annotation.aop.PointCut;
import io.swen90007sm2.alpheccaboot.bean.MethodCalling;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.aop.bean.IWeavePoint;
import io.swen90007sm2.alpheccaboot.core.aop.bean.StandardWeavePoint;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import io.swen90007sm2.alpheccaboot.core.aop.util.PatternUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 996Worker
 * @author com.github.jsoncat
 * @description interceptor for aop
 * @create 2022-08-16 13:39
 */
public class AspectInterceptor extends AbstractInterceptor {

    // advice bean is the target bean that defines enhance logics
    private final Object adviceBean;

    private final Set<String> expressionUrls = new HashSet<>();
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();


    public AspectInterceptor(Object adviceBean) {
        this.adviceBean = adviceBean;
        initAspectInterceptor();
    }

    private void initAspectInterceptor() {
        for (Method method : adviceBean.getClass().getMethods()) {
            PointCut pointCut = method.getAnnotation(PointCut.class);
            if (!ObjectUtils.isEmpty(pointCut)) {
                expressionUrls.add(pointCut.value());
            }

            Before before = method.getAnnotation(Before.class);
            if (!ObjectUtils.isEmpty(before)) {
                beforeMethods.add(method);
            }

            After after = method.getAnnotation(After.class);
            if (!ObjectUtils.isEmpty(after)) {
                afterMethods.add(method);
            }
        }
    }

    @Override
    public boolean supports(Object bean) {
        // if the bean name matches the pointCut expression, and exists enhance method in the interceptor
        boolean isBeanAppliesEnhance = expressionUrls.stream().anyMatch(
                url -> {
                    return PatternUtil.simpleMatch(url, bean.getClass().getName());
                }
        ) && (!(beforeMethods.isEmpty() && afterMethods.isEmpty()));

        return isBeanAppliesEnhance;
    }

    @Override
    public Object intercept(MethodCalling methodCalling) {

        IWeavePoint weavePoint = new StandardWeavePoint(
                adviceBean,
                methodCalling.getTargetObject(),
                methodCalling.getArgs()
        );

        // enhance before
        beforeMethods.forEach(
                method -> {
                    ReflectionUtil.invokeMethodWithoutResult(
                            adviceBean,
                            method,
                            weavePoint
                    );
                }
        );

        Object result = methodCalling.proceed();

        afterMethods.forEach(
                method -> {
                    ReflectionUtil.invokeMethodWithoutResult(
                            adviceBean,
                            method,
                            weavePoint
                    );
                }
        );

        return result;
    }
}