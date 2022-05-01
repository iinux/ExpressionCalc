package com.mycompany.helloworld.basic;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @Description 系统时钟并发测试
 */
@Slf4j
public class SystemClockTest {
    @Test
    // @JunitPerfConfig(threads = 1000)
    public void testSystemTime() {
        System.currentTimeMillis();
    }

    @Test
    // @JunitPerfConfig(threads = 1000)
    public void testSystemClockMultiThread() {
        SystemClock.now();
    }

    @Test
    public void testLog() {
        log.info("testLog");
    }

    @Test
    public void systemClockTest() throws InterruptedException {
        // 预热 long ready = SystemClock.now();
        int num = 1000;
        long start0 = System.currentTimeMillis();
        batchExe(num, () -> {
            for (int i = 0; i < 10; i++) {
                SystemClock.now();
            }
        });
        long end0 = System.currentTimeMillis();
        System.out.println("pre ready SystemClock Time:" + (end0 - start0) + "毫秒");

        // SystemClock
        long start1 = System.currentTimeMillis();
        batchExe(num, () -> {
            for (int i = 0; i < num; i++) {
                SystemClock.now();
            }
        });
        long end1 = System.currentTimeMillis();
        System.out.println("SystemClock Time:" + (end1 - start1) + "毫秒");

        // currentTimeMillis
        long start2 = System.currentTimeMillis();
        batchExe(num, () -> {
            for (int i = 0; i < num; i++) {
                System.currentTimeMillis();
            }
        });
        long end2 = System.currentTimeMillis();
        System.out.println("CurrentTimeMillis Time:" + (end2 - start2) + "毫秒");
    }

    private void batchExe(int num, Runnable runnable) {
        CompletableFuture[] futures = new CompletableFuture[num];
        for (int i = 0; i < num; i++) {
            futures[i] = CompletableFuture.runAsync(runnable);
        }

        CompletableFuture.allOf(futures).join();
    }

}
