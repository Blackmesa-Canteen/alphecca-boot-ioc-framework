package io.swen90007sm2.alpheccaboot.core.aop.bean;

/**
 * @author 996Worker
 * @description Abstract weave point, used to store some state of target object
 * @create 2022-08-16 13:56
 */
public interface IWeavePoint {

    /**
     * get the advice bean that contains enhancement logics
     */
    Object getAdviceBean();

    /**
     * get target object
     */
    Object getTarget();

    /**
     * get parameters from object array's method call
     */
    Object[] getArgs();
}