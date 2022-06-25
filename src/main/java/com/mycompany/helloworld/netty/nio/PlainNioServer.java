package com.mycompany.helloworld.netty.nio;

import org.junit.Test;
import sun.nio.ch.DefaultSelectorProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlainNioServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress address = new InetSocketAddress(8888);

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        ServerSocket ss = serverChannel.socket();
        ss.bind(address);

        Selector selector = Selector.open();
        System.out.println(selector.getClass());
        System.out.println(DefaultSelectorProvider.create().getClass());

        SelectionKey sk = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println(sk);

        ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        for (; ; ) {
            try {
                int number = selector.select();
                System.out.println(number);
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        // socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println("Accepted connection from " + socketChannel);
                    }

                    if (false)
                    if (key.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            if (socketChannel.write(buffer) == 0) {
                                break;
                            }
                        }
                        socketChannel.close();
                    }

                    // if (false)
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        int bytesRead = 0;
                        while (true) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                            byteBuffer.clear();

                            int read = socketChannel.read(byteBuffer);
                            if (read == 0) {
                                break;
                            } else if (read < 0) {
                                socketChannel.close();
                                break;
                            }

                            byteBuffer.flip();

                            Charset charset = Charset.forName("utf-8");
                            String receiveMsg = String.valueOf(charset.decode(byteBuffer).array());
                            System.out.println(receiveMsg);

                            byteBuffer.rewind();

                            socketChannel.write(byteBuffer);

                            bytesRead += read;
                        }

                        System.out.println("read " + bytesRead);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();

                    try {
                        key.channel().close();
                        key.cancel();
                    } catch (IOException cex) {
                        cex.printStackTrace();
                    }
                }
            }
        }
    }


    @Test
    public void availableCharsets() {
        Charset.availableCharsets().forEach((k ,v) -> {
            System.out.println(k + " " + v);
        });
    }

    @Test
    public void iso88591() {
        for (int i = 128; i < 256; i++) {
            System.out.println(new String(new byte[]{(byte) i}, StandardCharsets.ISO_8859_1));
        }
    }
}
