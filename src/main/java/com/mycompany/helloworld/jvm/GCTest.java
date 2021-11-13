package com.mycompany.helloworld.jvm;

// -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
// -XX:PretenureSizeThreshold=4194304 -XX:+UseSerialGC
// java -XX:+PrintCommandLineFlags -version
// -XX:MaxTenuringThreshold=5 -XX:+PrintTenuringDistribution

public class GCTest {
    public static void main(String[] args) {
        int size = 1024 * 1024;
        byte[] a1 = new byte[2*size];
        byte[] a2 = new byte[2*size];
        byte[] a3 = new byte[2*size];
        System.out.println("hello world");
    }
}
