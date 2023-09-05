package com.mycompany.helloworld.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RateLimiterStudy {
    @Test
    public void testSmoothBursty() {
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
        }
        /**
         * output: 基本上都是0.2s执行一次，符合一秒发放5个令牌的设定。
         * get 1 tokens: 0.0s
         * get 1 tokens: 0.182014s
         * get 1 tokens: 0.188464s
         * get 1 tokens: 0.198072s
         * get 1 tokens: 0.196048s
         * get 1 tokens: 0.197538s
         * get 1 tokens: 0.196049s
         */
    }

    @Test
    public void testSmoothBursty2() {
        RateLimiter r = RateLimiter.create(2);
        while (true)
        {
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /**
             * output:
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * end
             * get 1 tokens: 0.499796s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             */
        }
    }

    @Test
    public void testSmoothBursty3() {
        RateLimiter r = RateLimiter.create(5);
        while (true)
        {
            System.out.println("get 5 tokens: " + r.acquire(5) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /**
             * output:
             * get 5 tokens: 0.0s
             * get 1 tokens: 0.996766s 滞后效应，需要替前一个请求进行等待
             * get 1 tokens: 0.194007s
             * get 1 tokens: 0.196267s
             * end
             * get 5 tokens: 0.195756s
             * get 1 tokens: 0.995625s 滞后效应，需要替前一个请求进行等待
             * get 1 tokens: 0.194603s
             * get 1 tokens: 0.196866s
             */
        }
    }

    @Test
    public void testSmoothwarmingUp() {
        RateLimiter r = RateLimiter.create(2, 3, TimeUnit.SECONDS);
        while (true)
        {
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /**
             * output:
             * get 1 tokens: 0.0s
             * get 1 tokens: 1.329289s
             * get 1 tokens: 0.994375s
             * get 1 tokens: 0.662888s  上边三次获取的时间相加正好为3秒
             * end
             * get 1 tokens: 0.49764s  正常速率0.5秒一个令牌
             * get 1 tokens: 0.497828s
             * get 1 tokens: 0.49449s
             * get 1 tokens: 0.497522s
             */
        }
    }
}
