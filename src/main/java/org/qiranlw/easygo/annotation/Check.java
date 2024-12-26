package org.qiranlw.easygo.annotation;

import java.lang.annotation.*;

/**
 * @author qiranlw
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CheckContainer.class)
public @interface Check {

    String key() default "";
    String ex() default "";
    String msg() default "";
}
