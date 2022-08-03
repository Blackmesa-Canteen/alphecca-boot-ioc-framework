package io.swen90007sm2.alpheccaboot.core.aop.processor;

/**
 * An interface to do post process to existing bean, such as aop enhance
 *
 * @author xiaotian
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public interface IBeanPostProcessor {

    // default doing nothing
    default Object postProcessToBean(Object bean) {
        return bean;
    }
}
