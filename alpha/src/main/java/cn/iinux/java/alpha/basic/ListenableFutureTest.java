package cn.iinux.java.alpha.basic;

import com.google.common.util.concurrent.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.math.BigIntegerMath.factorial;

public class ListenableFutureTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadPool);
        ListenableFuture<BigInteger> guavaFuture = service.submit(()-> factorial(10));
        BigInteger result = guavaFuture.get();
        System.out.println(result);
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadPool);
        AsyncCallable<BigInteger> asyncCallable = Callables.asAsyncCallable(new Callable<BigInteger>() {
            public BigInteger call() {
                return factorial(10);
            }
        }, service);
        ListenableFuture<BigInteger> guavaFuture = Futures.submitAsync(asyncCallable, service);
        // System.out.println(guavaFuture.get());

        Futures.addCallback(
                guavaFuture,
                new FutureCallback<BigInteger>() {
                    public void onSuccess(BigInteger factorial) {
                        System.out.println(factorial);
                    }
                    public void onFailure(@NotNull Throwable thrown) {
                        System.out.println(thrown.getCause());
                    }
                },
                service);
    }
}
