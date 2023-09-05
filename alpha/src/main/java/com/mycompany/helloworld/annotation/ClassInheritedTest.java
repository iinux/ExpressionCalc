package com.mycompany.helloworld.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassInheritedTest {
    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @Inherited // 声明注解具有继承性
    @interface AInherited {
        String value() default "";
    }

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @Inherited // 声明注解具有继承性
    @interface BInherited {
        String value() default "";
    }

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    // 未声明注解具有继承性
    @interface CInherited {
        String value() default "";
    }

    @AInherited("父类的AInherited")
    @BInherited("父类的BInherited")
    @CInherited("父类的CInherited")
    static class SuperClass {
    }

    @BInherited("子类的BInherited")
    static class ChildClass extends SuperClass {
    }

    public static void main(String[] args) {
        Annotation[] annotations = ChildClass.class.getAnnotations();
        System.out.println(Arrays.toString(annotations));
        // output: [@annotations.InheritedTest1$AInherited(value=父类的AInherited), @annotations.InheritedTest1$BInherited(value=子类的BInherited)]
    }
}

class InheritedTest {

    @Target(value = {ElementType.METHOD, ElementType.FIELD})
    @Retention(value = RetentionPolicy.RUNTIME)
    @interface DESC {
        String value() default "";
    }

    static class SuperClass {
        @DESC("父类方法foo")
        public void foo() {
        }

        @DESC("父类方法bar")
        public void bar() {
        }

        @DESC("父类的属性")
        public String field;
    }

    static class ChildClass extends SuperClass {
        @Override
        public void foo() {
            super.foo();
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Method foo = ChildClass.class.getMethod("foo");
        System.out.println(Arrays.toString(foo.getAnnotations()));
        // output: []
        // 子类ChildClass重写了父类方法foo,并且@Override注解只在源码阶段保留，所以没有任何注解

        Method bar = ChildClass.class.getMethod("bar");
        System.out.println(Arrays.toString(bar.getAnnotations()));
        // output: [@annotations.InheritedTest$DESC(value=父类方法bar)]
        // bar方法未被子类重写，从父类继承到了原本注解

        Field field = ChildClass.class.getField("field");
        System.out.println(Arrays.toString(field.getAnnotations()));
        // output: [@annotations.InheritedTest$DESC(value=父类的属性)]
    }
}

class InterfaceInheritedTest {

    @Target(value = {ElementType.METHOD, ElementType.FIELD})
    @Retention(value = RetentionPolicy.RUNTIME)
    @interface DESC {
        String value() default "";
    }

    interface SuperInterface {
        @DESC("父接口的属性")
        String field = "field";
        @DESC("父接口方法foo")
        void foo();
        @DESC("父接口方法bar")
        default void bar() {

        }
    }

    interface ChildInterface extends SuperInterface {
        @DESC("子接口方法foo")
        @Override
        void foo();
    }

    static class ChildClass implements ChildInterface {
        @DESC("子类的属性")
        public String field = "field";
        @Override
        public void foo() {
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Method iFoo = ChildInterface.class.getMethod("foo");
        System.out.println(Arrays.toString(iFoo.getAnnotations()));
        // output: [@annotations.InterfaceInheritedTest$DESC(value=子接口方法foo)]

        Method iBar = ChildInterface.class.getMethod("bar");
        System.out.println(Arrays.toString(iBar.getAnnotations()));
        // output: [@annotations.InterfaceInheritedTest$DESC(value=父接口方法bar)]

        Field iField = ChildInterface.class.getField("field");
        System.out.println(Arrays.toString(iField.getAnnotations()));
        // output: [@annotations.InterfaceInheritedTest$DESC(value=父接口的属性)]

        Method foo = ChildClass.class.getMethod("foo");
        System.out.println(Arrays.toString(foo.getAnnotations()));
        // output: []; 被子类覆盖

        Method bar = ChildClass.class.getMethod("bar");
        System.out.println(Arrays.toString(bar.getAnnotations()));
        // output: [@annotations.InterfaceInheritedTest$DESC(value=父接口方法bar)]

        Field field = ChildClass.class.getField("field");
        System.out.println(Arrays.toString(field.getAnnotations()));
        // output: [@annotations.InterfaceInheritedTest$DESC(value=子类的属性)]
        // 是子类作用域下的属性`field`
    }
}
