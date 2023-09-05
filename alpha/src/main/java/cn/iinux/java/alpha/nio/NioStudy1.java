package cn.iinux.java.alpha.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioStudy1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            int rn = new SecureRandom().nextInt(20);
            buffer.put(rn);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
