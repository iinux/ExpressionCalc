package cn.iinux.java.alpha.jvm;

public class G1GcTest {
    // -verbose:gc -Xms10m -Xmx10m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:MaxGCPauseMillis=200m
    public static void main(String[] args) {
        int size = 1024 * 1024;
        byte[] a1 = new byte[size];
        byte[] a2 = new byte[size];
        byte[] a3 = new byte[size];
        byte[] a4 = new byte[size];
        System.out.println("hello g1");
    }
}
