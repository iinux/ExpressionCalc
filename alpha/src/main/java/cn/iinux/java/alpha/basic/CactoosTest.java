package cn.iinux.java.alpha.basic;


import com.google.common.math.LongMath;
import org.cactoos.func.Async;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CactoosTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        int number = 10;
        Async<Integer, Long> asyncFunction = new Async<>(LongMath::factorial);
        Future<Long> asyncFuture = asyncFunction.apply(number);
        long result = asyncFuture.get();
        System.out.println(result);
    }
}
