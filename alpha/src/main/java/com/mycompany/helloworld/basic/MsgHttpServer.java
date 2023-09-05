package com.mycompany.helloworld.basic;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class MsgHttpServer {
    protected String hostname = "0.0.0.0";
    protected int port = 8000;

    @Test
    public void run() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), 0);
        server.createContext("/msg", new MsgHandler());
        server.createContext("/postMsg", new PostMsgHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("started, listen " + hostname + " " + port);
        Thread.sleep(Long.MAX_VALUE);
    }

    static class MsgHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());

            Thread thread = new Thread(() -> {
                Dmb dmb = new Dmb();
                dmb.run(params.get("content"), params.get("title"), false);
            });
            thread.start();

            String response = "This is the response\n";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        public Map<String, String> queryToMap(String query) {
            Map<String, String> result = new HashMap<>();
            if (query == null) {
                return result;
            }
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }

    static class PostMsgHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) {
            // System.out.println("Serving the request");
            if (he.getRequestMethod().equalsIgnoreCase("POST")) {
                try {
                    Headers requestHeaders = he.getRequestHeaders();
                    Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();

                    int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));
                    // System.out.println("Content-length=" + contentLength);
                    InputStream is = he.getRequestBody();
                    OutputStream os = he.getResponseBody();
                    byte[] data = new byte[contentLength];
                    int length = is.read(data);

                    Headers responseHeaders = he.getResponseHeaders();
                    he.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);
                    os.write(data);

                    he.close();

                    Thread thread = new Thread(() -> {
                        Dmb dmb = new Dmb();
                        dmb.run(new String(data), "title", false);
                    });
                    thread.start();
                } catch (NumberFormatException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
