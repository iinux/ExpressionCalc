package cn.iinux.java.alpha.basic;

public class LibraryTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(System.getProperty("sun.boot.library.path"));
        System.loadLibrary("awt");
        System.loadLibrary("awtt");
    }
}
