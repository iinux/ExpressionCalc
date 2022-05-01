package com.mycompany.helloworld.basic;

import lombok.SneakyThrows;

public class ThreadDataTest {
    public int a = 1;
    public static void main(String[] args) {
        ThreadDataTest test = new ThreadDataTest();

        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    test.a++;
                    System.out.println("thread1 print " + test.a);
                }
            }
        }.start();

        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    System.out.println("thread2 print " + test.a);
                }
            }
        }.start();

    }
}
