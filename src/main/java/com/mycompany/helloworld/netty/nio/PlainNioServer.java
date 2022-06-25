package com.mycompany.helloworld.netty.nio;

import sun.nio.ch.DefaultSelectorProvider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioServer {
    public static void main(String[] args) throws IOException {
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
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = server.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());

                        System.out.println("Accepted connection from " + socketChannel);
                    }

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

                    if (true)
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        int bytesRead = 0;
                        while (true) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                            byteBuffer.clear();
                            int read = socketChannel.read(byteBuffer);
                            if (read <= 0) {
                                break;
                            }
                            byteBuffer.flip();
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
}
