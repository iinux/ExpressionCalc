package com.mycompany.helloworld.jvm;

import java.util.UUID;

public class InterfaceInit {
    public static void main(String[] args) {
        Interface2.run();
        System.out.println(Interface2.a);
        System.out.println(Interface2.b);
        // System.out.println(Interface2.c);
        Interface1 hello = new Hello();
        hello.hello();
    }
}

class Hello implements Interface1 {

    @Override
    public void hello() {
        System.out.println("i am hello");
    }
}

interface Interface1 {
    String b = "interface1 hello";

    String c = UUID.randomUUID().toString();

    void hello();

    Thread thread = new Thread() {
        {
            System.out.println("interface1 init");
        }
    };
}

interface Interface2 extends Interface1 {
    public static final String a = "interface2 hello";

    public static void run() {
        System.out.println("hello world");
    }
}
