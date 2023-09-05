package com.mycompany.helloworld.springboot.aliasfor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//编写元注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface AnnotationBase2 {
    String value() default "";
}

//编写子注解，其中子注解打上了元注解@AnnotationBase2标识
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AnnotationBase2
@interface AnnotationChildMultiAttribute {

    @AliasFor(annotation = AnnotationBase2.class, attribute = "value")
    String extendValue1() default "";

    @AliasFor(annotation = AnnotationBase2.class, attribute = "value")
    String extendValue2() default "";
}

//编写测试类
class ExtendMultiClass {

    @AnnotationChildMultiAttribute(extendValue1 = "extendValue1")
    public static class extendValue1 {
    }

    @AnnotationChildMultiAttribute(extendValue2 = "extendValue2")
    public static class extendValue2 {
    }
}

//编写测试代码
public class ExtendAnnotationMultiAttributeTest {
    @Test
    public void test() {
        AnnotationChildMultiAttribute mergedAnnotation1 = AnnotatedElementUtils.findMergedAnnotation(
                ExtendMultiClass.extendValue1.class, AnnotationChildMultiAttribute.class);
        assert mergedAnnotation1 != null;
        Assert.assertEquals("extendValue1", mergedAnnotation1.extendValue1());
        Assert.assertEquals("extendValue1", mergedAnnotation1.extendValue2());

        AnnotationChildMultiAttribute mergedAnnotation2 = AnnotatedElementUtils.findMergedAnnotation(
                ExtendMultiClass.extendValue2.class, AnnotationChildMultiAttribute.class);
        assert mergedAnnotation2 != null;
        Assert.assertEquals("extendValue2", mergedAnnotation2.extendValue1());
        Assert.assertEquals("extendValue2", mergedAnnotation2.extendValue2());
    }
}
