package cn.iinux.java.alpha.springboot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduleAnnotation {
    int expireTime() default 60 * 30;
}
