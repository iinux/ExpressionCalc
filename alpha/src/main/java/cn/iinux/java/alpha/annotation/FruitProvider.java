package cn.iinux.java.alpha.annotation;

import java.lang.annotation.*;

/**
 * 水果供应者注解
 *
 * @author peida
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {
    /**
     * 供应商编号
     *
     */
    int id() default -1;

    /**
     * 供应商名称
     *
     */
    String name() default "";

    /**
     * 供应商地址
     *
     */
    String address() default "";
}
