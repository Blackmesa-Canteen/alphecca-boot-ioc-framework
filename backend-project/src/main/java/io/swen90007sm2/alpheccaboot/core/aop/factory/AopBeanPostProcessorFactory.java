package io.swen90007sm2.alpheccaboot.core.aop.factory;

import io.swen90007sm2.alpheccaboot.core.aop.processor.ByteBuddyAopBeanProcessor;
import io.swen90007sm2.alpheccaboot.core.aop.processor.IBeanPostProcessor;
import io.swen90007sm2.alpheccaboot.core.aop.processor.JdkAopBeanProcessor;

/**
 * factor to generate correct proxy post processor for the target bean
 *
 * @author xiaotian
 */
public class AopBeanPostProcessorFactory {

    /**
     * get the bean post processor, if the bean implements interface, use JDK, else use CgLib
     */
    public static IBeanPostProcessor getCorrectBeanPostProcessor(Class<?> targetBean) {

        // use jdk proxy for class that has interface
        if (targetBean.isInterface() || targetBean.getInterfaces().length > 0) {
            return new JdkAopBeanProcessor();
        } else {

            // raplaced cglib to bytebuddy, because cglib can not support latest jdk
            // and stopped maintain in 2019
            return new ByteBuddyAopBeanProcessor();
//            return new CgLibAopBeanProcessor();
        }
    }
}
