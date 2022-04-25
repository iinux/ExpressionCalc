package com.mycompany.helloworld.netty.directbuffer;

import sun.misc.Cleaner;

public class TestCleaner {
    static class Obj {

    }
    public static void main(String[] args) {
        call();
        System.gc();
    }

    protected static void call() {
        Obj obj = new Obj();

        /*
        Cleaner.create(obj, () -> {
            System.out.println("cleaner run");
        });

         */
        MyOwnCleaner.clear(obj, () -> {
            System.out.println("cleaner run");
        });

    }
}
