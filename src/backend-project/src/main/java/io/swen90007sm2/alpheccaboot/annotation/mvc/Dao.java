package io.swen90007sm2.alpheccaboot.annotation.mvc;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;

import java.lang.annotation.*;

/**
 * an annotation used in data access level.
 *
 * @author xiaotian
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Dao {
}
