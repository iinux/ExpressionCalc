package com.mycompany.helloworld.tpe;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

interface QltRunnable {
    /**
     * 程序正常的执行逻辑
     */
    void run();

    /**
     * 当达到最大重试次数后异常的处理逻辑
     */
    void error(Exception e);
}

public class QltThreadPoolExecutor {

    private final ThreadPoolExecutor threadPoolExecutor;
    //重试次数
    private int retry = 3;
    //线程池大小
    private int size = 1;

    public QltThreadPoolExecutor() {
        //无参构造方法 默认线程数大小为电脑的核心数
        this(Runtime.getRuntime().availableProcessors());
    }

    public QltThreadPoolExecutor(int threadCount) {
        this.size = threadCount;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

    /**
     * 对外暴露的可执行的方法
     *
     */
    public void execute(final QltRunnable runnable) {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                execute(runnable, 0);
            }
        });
    }

    private void execute(final QltRunnable runnable, int retryCount) {
        try {
            runnable.run();
        } catch (Exception e) {
            //如果小于重试次数，则进行重试
            if (retryCount < retry) {
                execute(runnable, ++retryCount);
            } else {
                //超过设置的重试次数 则进行异常处理
                runnable.error(e);
            }
        }
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        threadPoolExecutor.setCorePoolSize(size);
        threadPoolExecutor.setMaximumPoolSize(size);
    }

}
