package com.mycompany.helloworld.nio;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioStudy4 {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileInputStream inputStream = new FileInputStream("input/hello.txt");
        FileOutputStream outputStream = new FileOutputStream("input/hello_o.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        // buffer = ByteBuffer.allocateDirect(512);
        // buffer = ByteBuffer.wrap(new byte[512]);

        while (true) {
            // buffer.clear();
            Thread.sleep(100);

            int read = inputChannel.read(buffer);

            System.out.println("read: " + read);

            if (-1 == read) {
                break;
            }

            buffer.flip();

            outputChannel.write(buffer);
        }

        inputChannel.close();
        outputChannel.close();
    }

    @Test
    public void s2() throws IOException {
        RandomAccessFile file = new RandomAccessFile("input/hello_o.txt", "rw");
        FileChannel channel = file.getChannel();

        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        buffer.put(0, (byte) 'a');
        buffer.put(3, (byte) 'b');

        file.close();
    }

    @Test
    public void s3() throws IOException {
        RandomAccessFile file = new RandomAccessFile("input/hello_o.txt", "rw");
        FileChannel channel = file.getChannel();

        FileLock lock = channel.lock(3, 6, true);
        System.out.println(lock.isValid());
        System.out.println(lock.isShared());

        lock.release();
        file.close();
    }

    @Test
    public void s4() throws IOException {
        ServerSocketChannel sChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        sChannel.socket().bind(address);

        int msgLen = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel channel = sChannel.accept();

        while (true) {
            int bytesRead = 0;
            while (bytesRead < msgLen) {
                long r = channel.read(buffers);
                bytesRead += r;

                System.out.println(bytesRead);
                Arrays.stream(buffers).map(buffer -> "position " + buffer.position() + " limit " + buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(Buffer::flip);

            long bytesWrite =  0;
            while (bytesWrite < msgLen) {
                long r = channel.write(buffers);
                bytesWrite += r;
            }

            Arrays.asList(buffers).forEach(Buffer::clear);

            System.out.println("read " + bytesRead + " write " + bytesWrite + "msgLen" + msgLen);
        }
    }
}
