package cn.iinux.java.alpha.jvm;

import java.util.ArrayList;
import java.util.List;

// -Xms5m -Xmx5m -XX:+HeapDumpOnOutOfMemoryError

public class OutOfMemoryTest {
    public static void main(String[] args) {
        List<OutOfMemoryTest> list = new ArrayList<>();
        for (;;) {
            list.add(new OutOfMemoryTest());
        }
    }
}
