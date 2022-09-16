package io.swen90007sm2.alpheccaboot.annotation.ioc;

import java.lang.annotation.*;

/**
 * annotation for lazy loaded bean, it won't be injected by AutoInjected, but need to call the
 * BeanManager.getLazyBeanByClass(SomeLazyClass.class);
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lazy {
}
