package com.mycompany.helloworld.basic;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CompletionServiceStudy {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        IntStream.range(0, 10).forEach(x -> {
            completionService.submit(() -> {
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println(Thread.currentThread().getName());
                return x;
            });
        });

        for (int i = 0; i < 10; i++) {
            System.out.println(completionService.take().get());
            
        }

        executor.shutdown();
    }
}
