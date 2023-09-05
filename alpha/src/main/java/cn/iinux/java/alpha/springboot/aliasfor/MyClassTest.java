package cn.iinux.java.alpha.springboot.aliasfor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.function.Consumer;

// https://www.cnblogs.com/54chensongxia/p/14385621.html
// https://blog.csdn.net/ab411919134/article/details/81782269

public class MyClassTest {
    @Test
    public void test1() throws NoSuchMethodException {
        Consumer<MyAliasAnnotation> logic = a -> {
            Assert.assertEquals("", "这是值", a.value());
            Assert.assertEquals("", a.value(), a.location());
        };

        MyAliasAnnotation aliasAnnotation = AnnotationUtils.findAnnotation(MyClass.class.getMethod("one"),
                MyAliasAnnotation.class);
        logic.accept(aliasAnnotation);
        MyAliasAnnotation a1 = MyClass.class.getMethod("one").getAnnotation(MyAliasAnnotation.class);
        // logic.accept(a1);

        MyAliasAnnotation aliasAnnotation2 = AnnotationUtils.findAnnotation(MyClass.class.getMethod("one2"),
                MyAliasAnnotation.class);
        logic.accept(aliasAnnotation2);
        MyAliasAnnotation a2 = MyClass.class.getMethod("one2").getAnnotation(MyAliasAnnotation.class);
        logic.accept(a2);

    }
}
