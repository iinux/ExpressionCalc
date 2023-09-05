package com.mycompany.helloworld.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

public class NioStudy5 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(8);
        buffer.putLong(18);
        buffer.putShort((short) 6);
        buffer.putDouble(1.8);
        buffer.putFloat((float) 1.7);
        buffer.putChar('我');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getFloat());
        System.out.println(buffer.getChar());
    }

    @Test
    public void s2() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        buffer.position(2);
        buffer.limit(6);

        ByteBuffer sliceBuffer = buffer.slice();

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

        ByteBuffer roBuffer = buffer.asReadOnlyBuffer();
        System.out.println(roBuffer.getClass());
        roBuffer.position(0);
        // roBuffer.put((byte) 2);
    }
}
