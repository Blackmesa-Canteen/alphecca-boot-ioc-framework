package io.swen90007sm2.alpheccaboot.core.aop.processor;


import io.swen90007sm2.alpheccaboot.core.aop.InterceptorManager;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Aop bean post processor, decorate beam with interceptors
 * @author xiaotian
 */
public abstract class AbstractAopBeanProcessor implements IBeanPostProcessor{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAopBeanProcessor.class);

    // decorate the target bean level by level with interceptor
    @Override
    public Object postProcessToBean(Object bean) {
        Object enhancedBean = bean;
        List<AbstractInterceptor> interceptorList = InterceptorManager.getInterceptorList();
        for (AbstractInterceptor interceptor : interceptorList) {
            if (interceptor.supports(bean)) {
                LOGGER.info("AOP enhanced [{}] with interceptor [{}]",
                        enhancedBean.getClass().getName(),
                        interceptor.getClass().getName());
                enhancedBean = enhanceBean(enhancedBean, interceptor);
            }
        }
        return enhancedBean;
    }

    // please design own enhance logic, now has 2 proxy, JDK and CgLib
    public abstract Object enhanceBean(Object bean, AbstractInterceptor interceptor);
}
