package com.mycompany.helloworld.basic;

import com.ea.async.Async;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static com.ea.async.Async.await;
import static com.google.common.math.LongMath.factorial;

public class EAAsyncTest {
    static {
        Async.init();
    }

    @Test
    public void factorialUsingEAAsync() {
        int number = 10;
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
        long result = await(completableFuture);
        System.out.println(result);
    }
}
