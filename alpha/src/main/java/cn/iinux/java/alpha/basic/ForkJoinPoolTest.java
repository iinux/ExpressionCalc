package cn.iinux.java.alpha.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.RecursiveTask;

// https://segmentfault.com/a/1190000039267451

@Slf4j
public class ForkJoinPoolTest {
    public static void main(String[] args) {
        int n = 20;

        // 为了追踪子线程名称，需要重写 ForkJoinWorkerThreadFactory 的方法
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("my-thread" + worker.getPoolIndex());
            return worker;
        };

        //创建分治任务线程池，可以追踪到线程名称
        ForkJoinPool forkJoinPool = new ForkJoinPool(4, factory, null, false);

        // 快速创建 ForkJoinPool 方法
        // ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        // 创建分治任务
        // Fibonacci fibonacci = new Fibonacci(n);
        Fibonacci2 fibonacci = new Fibonacci2(n);

        //调用 invoke 方法启动分治任务
        Integer result = forkJoinPool.invoke(fibonacci);
        log.info("Fibonacci {} 的结果是 {}", n, result);
    }
}

@Slf4j
class Fibonacci extends RecursiveTask<Integer> {
    final int n;
    Fibonacci(int n) {
        this.n = n;
    }

    @Override
    public Integer compute() {
        // 和递归类似，定义可计算的最小单元
        if (n <= 1) {
            return n;
        }
        log.info(Thread.currentThread().getName());

        Fibonacci f1 = new Fibonacci(n - 1);
        // 拆分成子任务
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        f2.fork();
        return f1.join() + f2.join();
        // f1.join 等待子任务执行结果
        //  return f2.compute() + f1.join();
    }
}

@Slf4j
class Fibonacci2 extends RecursiveTask<Integer> {
    final int n;
    Fibonacci2(int n) {
        this.n = n;
    }

    @Override
    public Integer compute() {
        // 和递归类似，定义可计算的最小单元
        if (n <= 1) {
            return n;
        }
        log.info(Thread.currentThread().getName());

        // 拆分成子任务
        Fibonacci2 f1 = new Fibonacci2(n - 1);
        Fibonacci2 f2 = new Fibonacci2(n - 2);
        invokeAll(f1, f2);
        return f1.join() + f2.join();
    }
}
