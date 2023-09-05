package cn.iinux.java.alpha.basic;

import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://colobu.com/2016/02/29/Java-CompletableFuture/
// https://www.liaoxuefeng.com/wiki/1252599548343744/1306581182447650

@Component
public class BasicFuture {
    @Test
    public void run0() {
        ExecutorService es = Executors.newFixedThreadPool(10);
        // es = Executors.newCachedThreadPool();
        Future<Integer> f = es.submit(() -> {
            // 长时间的异步计算
            // ……
            // 然后返回结果
            return 100;
        });
        // while(!f.isDone());
        try {
            System.out.println(f.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        es.shutdown();
    }

    @Test
    public void run1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("runAsync");
            }
        });
        // future.join();
        System.out.println(future.getNow(200));
        System.out.println(future.get());
        System.out.println(future2.get());
    }

    @Test
    public void run2() {
        final CompletableFuture<Integer> f = new CompletableFuture<>();
        class Client extends Thread {
            final CompletableFuture<Integer> f;

            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }

            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + ": " + f.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        new Client("Client1", f).start();
        new Client("Client2", f).start();
        System.out.println("waiting");
        f.complete(100);
        // f.completeExceptionally(new Exception());
        try {
            System.out.println(System.in.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int getMoreData() {
        long t = System.currentTimeMillis();
        Random rand = new Random();
        System.out.println("begin to start compute");
        try {
            // int a = 1/0;
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t) / 1000 + " seconds");
        return rand.nextInt(1000);
    }

    @Test
    public void run3() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(BasicFuture::getMoreData);
        BiConsumer<? super Integer, ? super Throwable> action = (v, e) -> {
            System.out.println("v:" + v);
            System.out.println("e:" + e);
        };
        Function<Throwable, ? extends Integer> fn = (e) -> {
            return 8;
        };
        Future<Integer> f = null;
        // f = future.whenComplete(action);
        // f = future.whenCompleteAsync(action);
        // f = future.exceptionally(fn);
        BiFunction<? super Integer, Throwable, Integer> fn2 = (v, e) -> {
            System.out.println(v);
            System.out.println(e);
            return 7;
        };
        // f = future.handle(fn2);
        f = future.handleAsync(fn2);
        System.out.println("after complete");
        System.out.println("f.get() = " + f.get());
        System.out.println("after get");
    }

    @Test
    public void run4() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<String> f = future.thenApplyAsync(i -> i * 10).thenApply(Object::toString);
        System.out.println(f.get()); //"1000"
    }

    @Test
    public void run5() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> f = null;
        // f = future.thenAccept(System.out::println);
        f = future.thenAcceptAsync(System.out::println);
        System.out.println(f.get());
    }

    @Test
    public void run6() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);

        CompletableFuture<Void> f = null;
        CompletableFuture<Integer> future2 = CompletableFuture.completedFuture(10);
        // f = future.thenAcceptBoth(future2, (x, y) -> System.out.println(x + ":" + y + ":" + x * y));
        f = future.runAfterBoth(future2, () -> {
            System.out.println("runAfterBoth");
        });
        System.out.println(f.get());
    }

    @Test
    public void run7() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        Runnable finished = () -> System.out.println("finished");
        CompletableFuture<Void> f = null;
        // f = future.thenRun(finished);
        f = future.thenRunAsync(finished);
        System.out.println(f.get());
    }

    @Test
    public void run8() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        Function<Integer, CompletionStage<String>> fn = i -> {
            return CompletableFuture.supplyAsync(() -> {
                return (i * 10) + "";
            });
        };
        CompletableFuture<String> f = null;
        // f = future.thenCompose(fn);
        f = future.thenComposeAsync(fn);
        System.out.println(f.get()); //1000
    }

    @Test
    public void run9() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "abc";
        });
        CompletableFuture<String> f = future.thenCombine(future2, (x, y) -> y + "-" + x);
        // thenCombineAsync
        System.out.println(f.get()); //abc-100
    }

    @Test
    public void run10() throws ExecutionException, InterruptedException {
        Random rand = new Random();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        // CompletableFuture<String> f =  future.applyToEither(future2, Object::toString);
        CompletableFuture<Void> f = future.acceptEither(future2, System.out::println);
        System.out.println(f.get());
    }

    @Test
    public void run11() throws ExecutionException, InterruptedException {
        Random rand = new Random();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<Void> f = CompletableFuture.allOf(future1, future2);
        // CompletableFuture<Object> f = CompletableFuture.anyOf(future1, future2);
        System.out.println(f.get());
    }

    @Test
    public void run12() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
        future.exceptionally(e -> {
            System.out.println(e.getMessage());
            return -1;
        });
        // future.join();
        System.out.println(future.get());
        System.out.println("end");
    }

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }
    public static <T> CompletableFuture<List<T>> sequence(Stream<CompletableFuture<T>> futures) {
        List<CompletableFuture<T>> futureList = futures.filter(Objects::nonNull).collect(Collectors.toList());
        return sequence(futureList);
    }
}
