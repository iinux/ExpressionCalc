package cn.iinux.java.alpha.jvm;

public class GCTest2 {
    // -verbose:gc -Xmx20m -Xms20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC
    public static void main(String[] args) {
        int size = 1024 * 1024;

        byte[] byte1 = new byte[4 *size];
        System.out.println("111");
        byte[] byte2 = new byte[4 *size];
        System.out.println("111");
        byte[] byte3 = new byte[4 *size];
        System.out.println("111");
        byte[] byte4 = new byte[2 *size];
        System.out.println("111");
    }
}
