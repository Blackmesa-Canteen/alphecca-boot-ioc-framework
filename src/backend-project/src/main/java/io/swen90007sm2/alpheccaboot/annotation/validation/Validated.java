package io.swen90007sm2.alpheccaboot.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for JSR303 validation to input param, marks a controller to be enhanced with validator
 * @author xiaotian
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validated {
}
