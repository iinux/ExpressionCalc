package cn.iinux.java.alpha.basic;

import com.jcabi.aspects.Async;
import com.jcabi.aspects.Loggable;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.google.common.math.LongMath.factorial;

// https://baeldung-cn.com/java-asynchronous-programming

public class JcabiAspectsTest {
    @Async
    @Loggable
    public Future<Long> factorialUsingAspect(int number) {
        Future<Long> factorialFuture = CompletableFuture.completedFuture(factorial(number));
        return factorialFuture;
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        System.out.println(factorialUsingAspect(10).get());
    }
}
