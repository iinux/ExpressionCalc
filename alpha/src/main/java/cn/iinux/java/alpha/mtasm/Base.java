package cn.iinux.java.alpha.mtasm;

import java.lang.management.ManagementFactory;

public class Base {
    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String s = name.split("@")[0];
        //打印当前Pid
        System.out.println("pid:" + s);
        while (true) {
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                break;
            }
            process2();
        }
    }

    public static void process2() {
        System.out.println("process2");
    }

    public void process() {
        System.out.println("process");

    }
}
