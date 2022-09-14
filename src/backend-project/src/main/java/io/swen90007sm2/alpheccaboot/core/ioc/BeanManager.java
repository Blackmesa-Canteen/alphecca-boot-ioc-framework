package io.swen90007sm2.alpheccaboot.core.ioc;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.aop.factory.AopBeanPostProcessorFactory;
import io.swen90007sm2.alpheccaboot.core.aop.processor.IBeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A core helper, which is a container to hold all objects.
 * By default, the bean scope is singleton only...
 *
 * @author xiaotian
 * @author tyshawnlee https://github.com/tyshawnlee
 */
public class BeanManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanManager.class);

    /*
     * the map contains all bean objects
     */
    private static final Map<Class<?>, Object> BEAN_MAP;

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    static {
        BEAN_MAP = new HashMap<>();

        // (Component) Controller + Blo + Dao
        Set<Class<?>> beanClassSet = ClassManager.getBeanClassSet();

        // instantiate objects from class object, then put in the map
        for (Class<?> clazz :beanClassSet) {
            if (clazz.getAnnotation(Lazy.class) == null) {
                Object object = ReflectionUtil.genNewInstanceByClass(clazz);
                BEAN_MAP.put(clazz, object);
            } else {
                // handle lazy beans
                BEAN_MAP.put(clazz, null);
            }

        }

        LOGGER.info("Instantiated {} instances in the bean container.", BEAN_MAP.size());
    }

    /**
     * get bean object from container by class object
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBeanFromBeanMapByClass(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            LOGGER.error("Can not get bean by class: [{}]", clazz.getName());
            throw new RuntimeException("Can not get bean by class object: " + clazz);
        }

        return (T) BEAN_MAP.get(clazz);
    }

    /**
     * called by outside to get lazy beans
     * @param clazz target class
     * @return lazy bean instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getLazyBeanByClass(Class<T> clazz) {
        Object instance = getBeanFromBeanMapByClass(clazz);

        // check existence first without lock to guarantee performance
        if (instance == null) {
            synchronized (BeanManager.class) {
                instance = getBeanFromBeanMapByClass(clazz);
                if (instance == null) {
                    instance = ReflectionUtil.genNewInstanceByClass(clazz);
                    BEAN_MAP.put(clazz, instance);
                    LOGGER.info("Lazy loaded: [{}]", instance.getClass().getName());
                }
            }
        }

        return (T) instance;
    }

    /**
     * put new bean in map
     */
    public static void putBean(Class<?> clazz, Object object) {
        BEAN_MAP.put(clazz, object);
    }

    /**
     * apply bean post processors to existing bean in the map.
     */
    public static void performBeanPostProcessorsToBeanMap() {
        BEAN_MAP.replaceAll((beanClass, beanInstance) -> {
            IBeanPostProcessor beanPostProcessor = AopBeanPostProcessorFactory.getCorrectBeanPostProcessor(beanClass);
            return beanPostProcessor.postProcessToBean(beanInstance);
        });
    }
}
