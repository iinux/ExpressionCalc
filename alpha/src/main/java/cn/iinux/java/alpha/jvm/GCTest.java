package cn.iinux.java.alpha.jvm;

// -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
// -XX:PretenureSizeThreshold=4194304 -XX:+UseSerialGC
// java -XX:+PrintCommandLineFlags -version
// -XX:MaxTenuringThreshold=5 -XX:+PrintTenuringDistribution
// -XX:TargetSurvivorRatio=60 -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -XX:+UseParNewGC

public class GCTest {
    public static void main1(String[] args) {
        int size = 1024 * 1024;
        byte[] a1 = new byte[2 * size];
        byte[] a2 = new byte[2 * size];
        byte[] a3 = new byte[2 * size];
        System.out.println("hello world");
    }

    // -XX:TargetSurvivorRatio=60 -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+PrintGCDetails -verbose:gc -Xmx200m -Xmn50m -XX:+PrintTenuringDistribution -XX:MaxTenuringThreshold=3
    public static void main(String[] args) throws InterruptedException {
        byte[] byte1 = new byte[512 * 1024];
        byte[] byte2 = new byte[512 * 1024];

        myGC();
        Thread.sleep(1000);
        System.out.println("111");
        myGC();
        Thread.sleep(1000);
        System.out.println("222");
        myGC();
        Thread.sleep(1000);
        System.out.println("333");
        myGC();
        Thread.sleep(1000);
        System.out.println("444");

        byte[] byte3 = new byte[1024 * 1024];
        byte[] byte4 = new byte[1024 * 1024];
        byte[] byte5 = new byte[1024 * 1024];

        myGC();
        Thread.sleep(1000);
        System.out.println("555");
        myGC();
        Thread.sleep(1000);
        System.out.println("666");

    }

    private static void myGC() {
        int i = 0;
        for (i = 0; i < 40; i++) {
            byte[] byte1 = new byte[1024*1024];
        }
    }
}
