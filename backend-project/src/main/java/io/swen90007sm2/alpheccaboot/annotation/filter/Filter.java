package io.swen90007sm2.alpheccaboot.annotation.filter;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;

import java.lang.annotation.*;

/**
 * Filter annotation marks a Class as a filter class.
 *
 * !!! ATTENTION !!! filter class need to implement IRequestFilter interface !
 * @author xiaotian
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Filter {
    String name();
}
