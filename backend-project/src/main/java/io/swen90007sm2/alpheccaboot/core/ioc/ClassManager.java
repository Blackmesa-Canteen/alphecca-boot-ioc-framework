package io.swen90007sm2.alpheccaboot.core.ioc;

import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Handler;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.config.ConfigFileManager;
import io.swen90007sm2.alpheccaboot.common.util.ClassLoadUtil;
import io.swen90007sm2.alpheccaboot.core.web.filter.IFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * a helper to hold all class objects
 *
 * @author xiaotian
 * @author tyshawnlee https://github.com/tyshawnlee
 */
public class ClassManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassManager.class);

    /**
     * a set holds all class object of this project
     */
    private static final Set<Class<?>> CLASS_SET;

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    static {
        String basePackage = ConfigFileManager.getBasePackageName();
        LOGGER.info("Scanning project base package: [{}]", basePackage);
        CLASS_SET = ClassLoadUtil.getClassSetUnderPackageName(basePackage);
        LOGGER.info("Scanned {} Classes and interfaces in total.", CLASS_SET.size());
    }

    /**
     * get set of class object whose class file annotated with @Handler
     * @return Set of Handler class objects
     */
    public static Set<Class<?>> getHandlerClassSet() {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Handler.class)) {
                set.add(clazz);
            }
        }

        LOGGER.info("Scanned {} Handler Classes.", set.size());
        return set;
    }

    public static Set<Class<?>> getBloClassSet() {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Blo.class)) {
                set.add(clazz);
            }
        }

        LOGGER.info("Scanned {} Blo Classes.", set.size());
        return set;
    }

    public static Set<Class<?>> getDaoClassSet() {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Dao.class)) {
                set.add(clazz);
            }
        }

        LOGGER.info("Scanned {} Dao Classes.", set.size());
        return set;
    }

    public static Set<Class<?>> getComponentAnnotatedClassSet() {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Component.class)) {
                set.add(clazz);
            }
        }

        LOGGER.info("Scanned {} Component Classes.", set.size());
        return set;
    }

    public static Set<Class<?>> getFilterAnnotatedClassSet() {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Filter.class)) {

                if (!ReflectionUtil.isClassImplementedInterface(clazz, IFilter.class)) {
                    LOGGER.error("Filter annotated class [{}] does not implemented IRequestFilter interface",
                            clazz.getName());
                } else {
                    set.add(clazz);
                }
            }
        }

        LOGGER.info("Scanned {} Filter Classes.", set.size());
        return set;
    }

    /**
     * Component: beans need to be instantiated. a bean combination of Blo and Handler and Dao.
     */
    public static Set<Class<?>> getComponentClassSet() {
        Set<Class<?>> set = new HashSet<>();
        set.addAll(getBloClassSet());
        set.addAll(getHandlerClassSet());
        set.addAll(getDaoClassSet());
        set.addAll(getComponentAnnotatedClassSet());
        return set;
    }

    /**
     * Bean: is a super set of the Component, in our project, component is enough to use.
     */
    public static Set<Class<?>> getBeanClassSet() {
        return getComponentClassSet();
    }

    /**
     * get class set with a specific Annotation class
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                set.add(clazz);
            }
        }

        return set;
    }

    /**
     * get class from his super class, used to hold class objects that are inhereted from an abstact class or interface
     */
    public static Set<Class<?>> getClassSetBySuperClass(Class<?> superClass) {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            // Determines if the class or interface represented by this Class object is either the same as,
            // or is a superclass or superinterface of, the class or interface represented
            // by the specified Class parameter.
            if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
                set.add(clazz);
            }
        }

        return set;
    }


}
