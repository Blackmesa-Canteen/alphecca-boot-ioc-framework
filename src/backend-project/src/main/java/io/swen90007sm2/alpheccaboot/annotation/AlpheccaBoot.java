package io.swen90007sm2.alpheccaboot.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AlpheccaBoot {
    String[] baseDir() default {};
}
