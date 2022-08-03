package io.swen90007sm2.alpheccaboot.annotation.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation used in methods that needs a request filter
 *
 * @author Xiaotian
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AppliesFilter {
    String[] filterNames();
}
