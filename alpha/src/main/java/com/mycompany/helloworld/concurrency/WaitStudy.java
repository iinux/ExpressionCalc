package com.mycompany.helloworld.concurrency;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class WaitStudy {
    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        synchronized (object) {
            object.wait();
        }
    }

    @Test
    @SuppressWarnings("all")
    public void waitNotify() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        Object lockA = new Object();
        Object lockB = new Object();

        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lockA) {
                    try {
                        lockA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }

                int n = counter.incrementAndGet();
                System.out.println(n);

                synchronized (lockB) {
                    lockB.notify();
                }

            }

        });
        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lockB) {
                    try {
                        lockB.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }

                int n = counter.addAndGet(-1);
                System.out.println(n);

                synchronized (lockA) {
                    lockA.notify();
                }

            }

        });

        thread1.start();
        thread2.start();

        Thread.sleep(1000);

        synchronized (lockA) {
            lockA.notify();
        }

        Thread.sleep(Long.MAX_VALUE);
    }
}
