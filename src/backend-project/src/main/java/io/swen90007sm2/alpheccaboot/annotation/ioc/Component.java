package io.swen90007sm2.alpheccaboot.annotation.ioc;

import java.lang.annotation.*;

/**
 * Component: Controller + Blo + Dao
 *
 * @author xiaotian
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String beanName() default "";
}
