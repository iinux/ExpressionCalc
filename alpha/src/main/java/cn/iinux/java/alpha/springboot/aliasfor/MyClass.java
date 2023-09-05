package cn.iinux.java.alpha.springboot.aliasfor;

@MyService
public class MyClass {
    @MyAliasAnnotation(location = "这是值")
    public static void one(){
    }

    @MyAliasAnnotation(value = "这是值")
    public static void one2(){
    }
}
