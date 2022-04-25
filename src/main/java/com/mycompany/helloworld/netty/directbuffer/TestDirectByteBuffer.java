package com.mycompany.helloworld.netty.directbuffer;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TestDirectByteBuffer {
    // -verbose:gc -XX:+PrintGCDetails -XX:MaxDirectMemorySize=40M
    // 加上-XX:+DisableExplicitGC,也会报OOM(Direct buffer memory)
    public static void main(String[] args) throws Exception {
        while (true) {
            ByteBuffer toBeDestroyed = ByteBuffer.allocateDirect(10 * 1024 * 1024);

            /*
            Method cleanerMethod = toBeDestroyed.getClass().getMethod("cleaner");
            cleanerMethod.setAccessible(true);
            Object cleaner = cleanerMethod.invoke(toBeDestroyed);
            Method cleanMethod = cleaner.getClass().getMethod("clean");
            cleanMethod.setAccessible(true);
            cleanMethod.invoke(cleaner);

             */
        }
    }
}
