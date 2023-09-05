package cn.iinux.java.alpha.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShareInteger {
    public static void main(String[] args) throws InterruptedException {
        class Bag {
            public volatile int a;
            public int b;
            final Lock lock = new ReentrantLock();

            public void increase() {
                lock.lock();
                try {
                    b++;
                } finally {
                    lock.unlock();
                }
            }
        }
        Bag bag = new Bag();
        Runnable runnable = () -> {
            for (int i = 0; i < 10000; i++) {
                bag.a++;
                bag.increase();
            }
        };

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            threadList.add(thread);
            thread.start();
        }

        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // thread1.interrupt();

        /*
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

         */

        System.out.println(bag.a);
        System.out.println(bag.b);

    }
}
