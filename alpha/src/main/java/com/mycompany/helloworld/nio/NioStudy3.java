package com.mycompany.helloworld.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioStudy3 {
    public static void main(String[] args) throws IOException {
        FileOutputStream stream = new FileOutputStream("input/hello2.txt");
        FileChannel channel = stream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        byte[] msg = "hello world".getBytes();

        for (int i = 0; i < msg.length; i++) {
            buffer.put(msg[i]);
        }

        buffer.flip();

        channel.write(buffer);
        stream.close();
    }
}
