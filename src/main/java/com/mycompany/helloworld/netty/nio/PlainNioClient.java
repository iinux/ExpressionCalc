package com.mycompany.helloworld.netty.nio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlainNioClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE);

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 25820));
        // socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));

        while (true) {
            Thread.sleep(1000);
            int number = selector.select();
            System.out.println("select number=" + number);
            Set<SelectionKey> keySet = selector.selectedKeys();
            System.out.println("selection key set size=" + keySet.size());

            for (SelectionKey selectionKey : keySet) {

                if (selectionKey.isConnectable()) {
                    System.out.println("connect event");

                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    if (client.isConnectionPending()) {
                        client.finishConnect();
                    }
                    extracted(client);
                    client.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) {
                    System.out.println("read event");

                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                    int count = client.read(readBuffer);
                    System.out.println("read count=" + count);
                    if (count == 0) {
                        break;
                    } else if (count < 0) {
                        client.close();
                        break;
                    } else {
                        String receiveMsg = new String(readBuffer.array(), 0, count);
                        System.out.println(receiveMsg);
                    }

                } else if (selectionKey.isWritable()) {
                    System.out.println("write event");

                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    extracted(client);
                    client.register(selector, SelectionKey.OP_READ);
                } else {
                    System.out.println("else event");
                }
            }

            keySet.clear();
        }
    }

    private static void extracted(SocketChannel client) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put("GET /".getBytes());
        writeBuffer.flip();
        client.write(writeBuffer);

        System.out.println("send finish");

        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
        executorService.submit(() -> {
            while (true) {
                System.out.println("you can input now:");

                writeBuffer.clear();

                InputStreamReader input = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(input);
                String sendMessage = br.readLine();

                writeBuffer.put(sendMessage.getBytes());
                writeBuffer.flip();
                client.write(writeBuffer);
            }
        });
    }
}
