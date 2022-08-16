package io.swen90007sm2.alpheccaboot.core.aop.bean;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author 996Worker
 * @author com.github.jsoncat
 * @create 2022-08-16 13:59
 */
public class StandardWeavePoint implements IWeavePoint {

    private final Object adviceBean;
    private final Object target;
    private Object[] args;

    public StandardWeavePoint(Object adviceBean, Object target, Object[] args) {
        this.adviceBean = adviceBean;
        this.target = target;
        this.args = args;
    }

    @Override
    public Object getAdviceBean() {
        return adviceBean;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object[] getArgs() {
        if (ArrayUtils.isEmpty(args)) {
            args = new Object[0];
            return args;
        } else {
            Object[] argsCopy = new Object[args.length];
            System.arraycopy(args, 0, argsCopy, 0, args.length);
            return argsCopy;
        }
    }
}