package cn.iinux.java.alpha.concurrency;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class WaitStudy {
    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        WaitStudy ws = new WaitStudy();
        ws.park3();
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

    @Test
    public void park() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("before park");
            LockSupport.park();
            System.out.println("after park");
        });
        thread1.start();

        System.out.println("before sleep");
        Thread.sleep(1000);
        System.out.println("after sleep");
        LockSupport.unpark(thread1);

        System.out.println("pause");
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void park2() throws InterruptedException {
        Object blocker = new Object();
        Thread thread1 = new Thread(() -> {
            System.out.println("before park");
            LockSupport.park(blocker);
            System.out.println("after park");
        });
        thread1.start();

        System.out.println("before sleep");
        Thread.sleep(1000);
        System.out.println("after sleep");
        LockSupport.unpark(thread1);

        System.out.println("pause");
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void park3() throws InterruptedException {
        LockSupport.park(1);
    }
}
