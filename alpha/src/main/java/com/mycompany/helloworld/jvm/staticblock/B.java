package com.mycompany.helloworld.jvm.staticblock;

public class B {
    static {
        System.out.println("b static block");
        A.say();
    }
    public static void say() {
        System.out.println("i am b say");
    }
}
