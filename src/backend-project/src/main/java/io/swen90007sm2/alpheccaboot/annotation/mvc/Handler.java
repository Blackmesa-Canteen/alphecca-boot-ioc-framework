package io.swen90007sm2.alpheccaboot.annotation.mvc;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;

import java.lang.annotation.*;

/**
 * An annotation used in a class in the handler level.
 *
 *  level structure:  Handler -> Blo -> Dao
 *
 * @author Xiaotian
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Handler {

    /**
     * Request URL/Path
     * @return String
     */
    String path() default "";
}
