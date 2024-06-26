package cn.iinux.java.alpha.nio;

/*
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class AsyncHttpJDK {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api1"))
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api2"))
                .build();

        CompletableFuture<HttpResponse<String>> response1 = client.sendAsync(request1, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> response2 = client.sendAsync(request2, HttpResponse.BodyHandlers.ofString());

        response1.thenAccept(res -> System.out.println("Response 1: " + res.body()));
        response2.thenAccept(res -> System.out.println("Response 2: " + res.body()));

        // Combine futures to wait for both to complete
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(response1, response2);
        combinedFuture.join();  // Wait for both requests to complete
    }
}

 */
