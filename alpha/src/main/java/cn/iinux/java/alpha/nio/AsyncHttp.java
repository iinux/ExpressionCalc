package cn.iinux.java.alpha.nio;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import java.util.concurrent.Future;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.web.reactive.function.client.WebClient;

public class AsyncHttp {
    @Test
    public void main0() throws Exception {
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();

        HttpGet request1 = new HttpGet("http://example.com/api1");
        HttpGet request2 = new HttpGet("http://example.com/api2");

        Future<HttpResponse> future1 = client.execute(request1, null);
        Future<HttpResponse> future2 = client.execute(request2, null);

        HttpResponse response1 = future1.get();
        HttpResponse response2 = future2.get();

        System.out.println("Response 1: " + response1.getStatusLine());
        System.out.println("Response 2: " + response2.getStatusLine());

        client.close();
    }

    @Test
    public void main1() {
        HttpClient client = HttpClient.create();

        Mono<String> response1 = client.get()
                .uri("http://example.com/api1")
                .responseContent()
                .aggregate()
                .asString();

        Mono<String> response2 = client.get()
                .uri("http://example.com/api2")
                .responseContent()
                .aggregate()
                .asString();

        response1.zipWith(response2)
                .doOnNext(tuple -> {
                    System.out.println("Response 1: " + tuple.getT1());
                    System.out.println("Response 2: " + tuple.getT2());
                })
                .block();
    }

    @Test
    public void main2() {
        OkHttpClient client = new OkHttpClient();

        Request request1 = new Request.Builder()
                .url("http://example.com/api1")
                .build();

        Request request2 = new Request.Builder()
                .url("http://example.com/api2")
                .build();

        Single<Response> response1 = Single.fromCallable(() -> client.newCall(request1).execute())
                .subscribeOn(Schedulers.io());

        Single<Response> response2 = Single.fromCallable(() -> client.newCall(request2).execute())
                .subscribeOn(Schedulers.io());

        Single.zip(response1, response2, (res1, res2) -> {
            System.out.println("Response 1: " + res1.body().string());
            System.out.println("Response 2: " + res2.body().string());
            return "Done";
        }).blockingGet();
    }

    @Test
    public void main3() {
        WebClient client = WebClient.create();

        Mono<String> response1 = client.get()
                .uri("http://www.example.com/")
                .retrieve()
                .bodyToMono(String.class);

        Mono<String> response2 = client.get()
                .uri("http://www.360.cn/")
                .retrieve()
                .bodyToMono(String.class);

        response1.zipWith(response2)
                .doOnNext(tuple -> {
                    System.out.println("Response 1: " + tuple.getT1());
                    System.out.println("Response 2: " + tuple.getT2());
                })
                .block();
    }
}
