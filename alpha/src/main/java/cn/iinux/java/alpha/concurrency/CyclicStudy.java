package cn.iinux.java.alpha.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class CyclicStudy {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("barrier action");
        });
        IntStream.range(0, 3).forEach(i -> {
            new Thread(() -> {
                System.out.println("start " + i);
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("end " + i);
            }).start();
        });
    }
}
