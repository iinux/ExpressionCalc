package cn.iinux.java.alpha.basic;

import sun.misc.Launcher;
import sun.reflect.Reflection;

import java.util.stream.Stream;

public class SystemProperty {
    public static void main(String[] args) {
        printProperty("sun.boot.class.path");
        printProperty("java.ext.dirs");
        printProperty("java.class.path");
        printProperty("java.system.class.loader");

        System.getProperties().list(System.out);

        System.out.println(ClassLoader.class.getClassLoader());
        System.out.println(Launcher.class.getClassLoader());
        System.out.println(Thread.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());

        // System.out.println(Reflection.getCallerClass());
    }

    public static void printProperty(String name) {
        System.out.println("name = " + name);
        String property = System.getProperty(name);
        if (property == null) {
            System.out.println("is null");
            return;
        }
        Stream.of(property.split(":")).forEach(System.out::println);
    }
}
