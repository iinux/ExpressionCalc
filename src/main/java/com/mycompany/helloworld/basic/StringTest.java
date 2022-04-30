package com.mycompany.helloworld.basic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringTest {
    @Test
    public void stringJoin() {
        List<String> list = new ArrayList<>();
        System.out.println("[" + String.join(",", list) + "]");
    }

    public static String BASE_INFO = "Mr.Y";
    public static final int COUNT = 2000000;

    /**
     * 执行一项String赋值测试
     */
    @SuppressWarnings("all")
    public static void doStringTest() {
        String str = new String(BASE_INFO);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT / 100; i++) {
            str = str + "miss";
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " millis has cost when used String.");
    }

    /**
     * 执行一项StringBuffer赋值测试
     */
    @SuppressWarnings("all")
    public static void doStringBufferTest() {

        StringBuffer sb = new StringBuffer(BASE_INFO);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            sb.append("miss");
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " millis has cost when used StringBuffer.");
    }

    /**
     * 执行一项StringBuilder赋值测试
     */
    @SuppressWarnings("all")
    public static void doStringBuilderTest() {
        StringBuilder sb = new StringBuilder(BASE_INFO);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            sb.append("miss");
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " millis has cost when used StringBuilder.");
    }

    /**
     * 测试StringBuffer遍历赋值结果
     *
     */
    @SuppressWarnings("all")
    public static void doStringBufferListTest(List<String> list) {
        StringBuffer sb = new StringBuffer();
        long startTime = System.currentTimeMillis();
        for (String string : list) {
            sb.append(string);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(sb + "buffer cost:" + (endTime - startTime) + " millis");
    }

    /**
     * 测试StringBuilder迭代赋值结果
     *
     */
    public static void doStringBuilderListTest(List<String> list) {
        StringBuilder sb = new StringBuilder();
        long startTime = System.currentTimeMillis();
        for (String s : list) {
            sb.append(s);
        }

        long endTime = System.currentTimeMillis();
        System.out.println(sb + "builder cost:" + (endTime - startTime) + " millis");
    }

    public static void main(String[] args) {
        doStringTest();
        doStringBufferTest();
        doStringBuilderTest();

        List<String> list = new ArrayList<>();
        list.add(" I ");
        list.add(" like ");
        list.add(" BeiJing ");
        list.add(" tian ");
        list.add(" an ");
        list.add(" men ");
        list.add(" . ");

        doStringBufferListTest(list);
        doStringBuilderListTest(list);
    }
}
