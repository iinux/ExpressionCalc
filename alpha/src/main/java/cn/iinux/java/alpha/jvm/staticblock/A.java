package cn.iinux.java.alpha.jvm.staticblock;

public class A {
    static {
        System.out.println("A static block");
    }
    public static void say() {
        System.out.println("i am a say");
    }
}
