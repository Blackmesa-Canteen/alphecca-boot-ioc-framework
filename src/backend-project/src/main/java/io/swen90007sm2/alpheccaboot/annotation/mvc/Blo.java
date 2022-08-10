package io.swen90007sm2.alpheccaboot.annotation.mvc;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;

import java.lang.annotation.*;

/**
 * An annotation that used in a class to mark it as a component of business logic level.
 * level structure:  Controller -> Blo -> Dao
 *
 * @author Xiaotian
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Blo {
}
