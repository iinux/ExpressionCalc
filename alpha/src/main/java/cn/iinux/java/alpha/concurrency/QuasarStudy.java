package cn.iinux.java.alpha.concurrency;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;

public class QuasarStudy {
    // -javaagent:quasar-core-0.7.4-jdk8.jar
    // https://www.cnblogs.com/jmcui/p/12511623.html
    public static void main(String[] args) {
        new Fiber<Void>() {
            @Override
            protected Void run() throws SuspendExecution, InterruptedException {
                System.out.println("Hello Fiber1");
                return null;
            }
        }.start();
        new Fiber<>(() -> {
            System.out.println("Hello Fiber2");
            return null;
        }).start();
    }
}
