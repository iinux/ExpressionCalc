package com.mycompany.helloworld.annotation;

import java.lang.annotation.*;


/**
 * 水果名称注解
 *
 * @author johann
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}
