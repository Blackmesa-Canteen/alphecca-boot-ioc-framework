package io.swen90007sm2.alpheccaboot.annotation.filter;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
