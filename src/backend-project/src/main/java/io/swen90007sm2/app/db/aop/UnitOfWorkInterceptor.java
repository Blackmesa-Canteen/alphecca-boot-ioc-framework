package io.swen90007sm2.app.db.aop;

import io.swen90007sm2.alpheccaboot.bean.MethodCalling;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import io.swen90007sm2.alpheccaboot.core.aop.util.PatternUtil;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;

/**
 * @author 996Worker
 * @description interceptor for Unit of Work
 * @create 2022-08-16 15:32
 */
public class UnitOfWorkInterceptor extends AbstractInterceptor {

    public final static String CONTROLLER_PATTERN = "io.swen90007sm2.app.controller.*.*Controller*";

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean supports(Object bean) {
        if (bean != null) {
            return PatternUtil.simpleMatch(CONTROLLER_PATTERN, bean.getClass().getName());
        } else {
            return false;
        }

    }

    @Override
    public Object intercept(MethodCalling methodCalling) {
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
        Object result = methodCalling.proceed();
        UnitOfWorkHelper.getCurrent().commit();
        return result;
    }
}