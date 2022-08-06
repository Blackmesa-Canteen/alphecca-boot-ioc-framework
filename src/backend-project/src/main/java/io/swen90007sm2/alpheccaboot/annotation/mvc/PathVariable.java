package io.swen90007sm2.alpheccaboot.annotation.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for request. e.g.
 *
 * public R getUser(@PathVariable("id") Integer id);
 * then the Integer id will be http://localhost:8088/user/{id} 's id
 *
 * @author Xiaotian
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathVariable {
    String value();
}
