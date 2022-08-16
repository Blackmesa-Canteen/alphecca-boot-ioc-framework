package io.swen90007sm2.app.db.aop;

import io.swen90007sm2.alpheccaboot.annotation.aop.After;
import io.swen90007sm2.alpheccaboot.annotation.aop.Aspect;
import io.swen90007sm2.alpheccaboot.annotation.aop.Before;
import io.swen90007sm2.alpheccaboot.annotation.aop.PointCut;
import io.swen90007sm2.alpheccaboot.core.aop.bean.IWeavePoint;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;

/**
 * @author 996Worker
 * @description enhancement object for uow logic
 * @create 2022-08-16 14:30
 */
//@Aspect
public class UnitOfWorkAdvise {

    @PointCut("io.swen90007sm2.app.controller.*.*Controller*")
    public void allController() {}

    @Before
    public void initUnitOfWorkHelper (IWeavePoint weavePoint) {
        UnitOfWorkHelper.init(CacheUtil.getObjectCacheInstance());
    }

    @After
    public void commitWorks (Object result, IWeavePoint weavePoint) {
        UnitOfWorkHelper.getCurrent().commit();
    }
}