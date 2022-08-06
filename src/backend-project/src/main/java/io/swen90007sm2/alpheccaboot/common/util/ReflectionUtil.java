package io.swen90007sm2.alpheccaboot.common.util;

import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Reflection Utils, including instantiate new object, invoke a method and so on, based on class object loaded by ClassLoadUtil.
 *
 * @author Xiaotian
 * @author tyshawnlee https://github.com/tyshawnlee/handwritten-mvc
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * instantiate an object based a class object
     */
    public static Object genNewInstanceByClass(Class<?> clazz) {
        Object instance;
        try {
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            instance = declaredConstructor.newInstance();
            declaredConstructor.setAccessible(false);
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * invoke a method in an object
     */
    public static Object invokeMethod(Object object, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(object, args);
        } catch (Throwable e) {
            if (e.getCause() != null && e.getCause() instanceof ConstraintViolationException) {
                LOGGER.error("invoke method param validation, exception: " + e);
                throw (ConstraintViolationException) e.getCause();
            } else if (e.getCause() instanceof RequestException) {
                throw (RequestException) e.getCause();
            } else if (e.getCause() instanceof InternalException) {
                throw (InternalException) e.getCause();
            }
            LOGGER.error("invoke method failure, exception: ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void invokeMethodWithoutResult(Object targetObject, Method method, Object... args) {
        try {
            // invoke target method through reflection
            method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("invoke method failure, exception: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * brutally set a field in an object with reflection
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true); // overcome private bug
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("set field failure, exception: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * get all methods from this class
     */
    public static Method[] getMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    /**
     * Get the implementation classes of the interface
     */
    public static <T> Set<Class<? extends T>> getSubClass(String packageName, Class<T> interfaceClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(interfaceClass);
    }

    public static boolean isClassImplementedInterface(Class<?> clazz, Class<?> theInterface) {
        return theInterface.isAssignableFrom(clazz);
    }

}
