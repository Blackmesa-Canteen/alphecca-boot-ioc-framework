package io.swen90007sm2.alpheccaboot.core;

import io.swen90007sm2.alpheccaboot.core.aop.InterceptorManager;
import io.swen90007sm2.alpheccaboot.core.config.ConfigFileManager;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.core.ioc.ClassManager;
import io.swen90007sm2.alpheccaboot.core.ioc.InjectionHelper;
import io.swen90007sm2.alpheccaboot.core.mvc.ControllerManager;
import io.swen90007sm2.alpheccaboot.common.util.ClassLoadUtil;
import io.swen90007sm2.alpheccaboot.core.web.filter.FilterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Application context loader
 * init the framework, perform class scan, and dependency injection
 *
 * @author xiaotain
 */
public class  AppContextLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppContextLoader.class);

    /**
     * Load 5 helper class, run static blocks in them.
     */
    public static void initAppContext() {

        // the order should not be changed!
        // Instantiate all beans before Injection can avoid circular reference issue.
        Class<?>[] classLoadList = {
                ConfigFileManager.class,
                ClassManager.class,
                BeanManager.class,
                InterceptorManager.class,
                InjectionHelper.class,
                FilterManager.class,
                ControllerManager.class
        };

        // run static blocks one by one
        for (Class<?> clazz : classLoadList) {
            ClassLoadUtil.loadClass(clazz.getName());
            LOGGER.info("context " + clazz.getName() + " loaded.");
        }

        // perform AOP in BEAN_MAP. bean map includes Component (Handler + Blo + Dao)
        BeanManager.performBeanPostProcessorsToBeanMap();

        LOGGER.info("IoC Container is ready to go.");
    }
}
