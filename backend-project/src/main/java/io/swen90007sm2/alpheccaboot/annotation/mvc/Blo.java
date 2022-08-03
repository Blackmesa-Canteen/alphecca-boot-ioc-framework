package io.swen90007sm2.alpheccaboot.annotation.mvc;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that used in a class to mark it as a component of business logic level.
 * level structure:  Handler -> Blo -> Dao
 *
 * @author Xiaotian
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Blo {
}
