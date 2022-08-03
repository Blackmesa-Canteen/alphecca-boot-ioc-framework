package io.swen90007sm2.alpheccaboot.annotation.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for request. e.g.
 *
 * public R get(@RequestParam(value = "name") String name);
 * then the Integer id will be http://localhost:8088/user?name=somename 's somename
 *
 * @author Xiaotian
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {
    String value();

    boolean require() default false;

    String defaultValue() default "";
}
