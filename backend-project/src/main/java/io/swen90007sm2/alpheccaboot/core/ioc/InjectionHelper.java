package io.swen90007sm2.alpheccaboot.core.ioc;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.aop.factory.AopBeanPostProcessorFactory;
import io.swen90007sm2.alpheccaboot.core.aop.processor.IBeanPostProcessor;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * core helper for dependency injection
 *
 * @author xiaotian
 */
public class InjectionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(InjectionHelper.class);

    static {
        Map<Class<?>, Object> beanMap = BeanManager.getBeanMap();
        performInjection(beanMap);
    }

    private static void performInjection(Map<Class<?>, Object> beanMap) {
        if(!beanMap.isEmpty()) {
            // traverse this bean map
            // Using iterator has the best performance
            Iterator<Map.Entry<Class<?>, Object>> iterator = beanMap.entrySet().iterator();
            while (iterator.hasNext()) {
                // traverse each bean map entry
                Map.Entry<Class<?>, Object> next = iterator.next();
                Class<?> clazz = next.getKey();
                Object beanInstance = next.getValue();

                // get fields with reflection, then ready to inject
                Field[] fields = clazz.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(fields)) {
                    injectBeanToFields(beanMap, beanInstance, fields);
                }
            }
        }
    }

    private static void injectBeanToFields(Map<Class<?>, Object> beanMap, Object beanInstance, Field[] fields) {
        for (Field field : fields) {
            // judge whether the field has @AutoInjected or not
            if (field.isAnnotationPresent(AutoInjected.class)) {
                // inject the required bean to this field of this bean
                // find bean by Class/type
                Class<?> fieldClass = field.getType();

                // usually, we use interface or super class as type,
                // so need to find the implementation class
                Class<?> fieldImplClass = findImplementation(fieldClass);

                // get object instance from bean map
                Object fieldInstance = beanMap.get(fieldImplClass);
                if (fieldInstance != null) {
                    // if beanInstance exists, do injection

                    // AOP enhanced object injection to the field
                    IBeanPostProcessor beanPostProcessor = AopBeanPostProcessorFactory.getCorrectBeanPostProcessor(field.getType());
                    fieldInstance = beanPostProcessor.postProcessToBean(fieldInstance);

                    // attach the bean result to the field
                    ReflectionUtil.setField(beanInstance, field, fieldInstance);

                    LOGGER.info("Dependency Injection: Bean [{}] to [{}]'s field [{}]",
                            fieldInstance.getClass().getName(),
                            beanInstance.getClass().getName(),
                            fieldClass.getName()
                            );
                }
            }
        }
    }

    private static Class<?> findImplementation(Class<?> fieldClass) {
        Class<?> res = fieldClass;
        // find impl/son class based on super
        Set<Class<?>> set = ClassManager.getClassSetBySuperClass(fieldClass);
        if (!set.isEmpty()) {
            // get the first impl class
            res = set.iterator().next();
        }
        return res;
    }
}
