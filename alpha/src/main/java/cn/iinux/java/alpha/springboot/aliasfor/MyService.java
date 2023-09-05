package cn.iinux.java.alpha.springboot.aliasfor;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MyService {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
