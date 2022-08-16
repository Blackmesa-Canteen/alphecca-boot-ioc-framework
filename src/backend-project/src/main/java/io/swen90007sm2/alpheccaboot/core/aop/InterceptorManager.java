package io.swen90007sm2.alpheccaboot.core.aop;

import io.swen90007sm2.alpheccaboot.annotation.aop.Aspect;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.AbstractInterceptor;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.aop.AspectInterceptor;
import io.swen90007sm2.alpheccaboot.core.aop.interceptor.validation.JSR303ValidationInterceptor;
import io.swen90007sm2.alpheccaboot.core.config.ConfigFileManager;
import io.swen90007sm2.alpheccaboot.core.ioc.ClassManager;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * helper to load all interceptors.
 *
 * @author xiaotian
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public class InterceptorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorManager.class);

    private static List<AbstractInterceptor> INTERCEPTOR_LIST;

    static {
        INTERCEPTOR_LIST = new ArrayList<>();
        String basePackage = ConfigFileManager.getBasePackageName();

        // get aspect classes
        Set<Class<?>> aspectAnnotatedClasses = ClassManager.getAspectAnnotatedClassSet();

        // get subclasses that implmented Interceptor abstract class
        Set<Class<? extends AbstractInterceptor>> interceptorClasses = ReflectionUtil.getSubClass(basePackage, AbstractInterceptor.class);

        // traverse all aspect classes and instantiate them
        aspectAnnotatedClasses.forEach(clazz -> {
            try{
                Object instance = ReflectionUtil.genNewInstanceByClass(clazz);
                AbstractInterceptor interceptor = new AspectInterceptor(instance);
                interceptor.setOrder(clazz.getAnnotation(Aspect.class).order());

                INTERCEPTOR_LIST.add(interceptor);
            } catch (Exception e) {
                LOGGER.error("init constructor for aspect exception: " + clazz.getName());
                throw new InternalException("Server error, try again later");
            }
        });

        // traverse all intercepter classes and instantiate them
        interceptorClasses.forEach(clazz -> {
            try {
                INTERCEPTOR_LIST.add(clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                LOGGER.error("init constructor for interceptor exception: " + clazz.getName());
                LOGGER.error("exception ",e);
                throw new InternalException("Server error, try again later");
            }
        });

        // add jsr303 interceptor
        INTERCEPTOR_LIST.add(new JSR303ValidationInterceptor());

        // sort interceptor list by order
        INTERCEPTOR_LIST = INTERCEPTOR_LIST.stream().sorted(
                Comparator.comparing(AbstractInterceptor::getOrder)
        ).collect(Collectors.toList());

        LOGGER.info("Instantiated {} interceptors.", INTERCEPTOR_LIST.size());
    }

    public static List<AbstractInterceptor> getInterceptorList() {
        return INTERCEPTOR_LIST;
    }
}
