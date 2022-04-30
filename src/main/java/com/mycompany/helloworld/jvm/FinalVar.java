package com.mycompany.helloworld.jvm;

public class FinalVar {
    public static void main(String[] args) {
        System.out.println(FinalVar2.a);
    }
}

class FinalVar2 {
    public static final int a = 5;

    static {
        System.out.println("final var 2");
    }
}
