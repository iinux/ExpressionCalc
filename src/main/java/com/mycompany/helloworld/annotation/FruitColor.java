package com.mycompany.helloworld.annotation;

import java.lang.annotation.*;

/**
 * 水果颜色注解
 *
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     *
     */
    enum Color {BLUE, RED, GREEN}

    ;

    /**
     * 颜色属性
     *
     */
    Color fruitColor() default Color.GREEN;
}
