package cn.iinux.java.alpha.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioStudy2 {
    public static void main(String[] args) throws IOException {
        FileInputStream stream = new FileInputStream("input/hello.txt");
        FileChannel channel = stream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        channel.read(buffer);

        buffer.flip();

        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.println("char " + (char)b);
        }

        stream.close();
    }
}
