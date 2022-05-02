package com.mycompany.helloworld.springboot.apiversion;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    /**
     * 标识版本号，从1.0.0.0开始
     */
    String value() default "1.0.0.0";
}
