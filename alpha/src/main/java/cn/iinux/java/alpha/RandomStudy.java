package cn.iinux.java.alpha;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomStudy {

    @Test
    public void threadLocalTest() {
        int a = ThreadLocalRandom.current().nextInt();
        System.out.println(a);
        Random random = new Random();
        System.out.println(random.nextInt());
    }


    @Test
    public void unsafe() throws NoSuchFieldException, IllegalAccessException {
        // Unsafe 设置了构造方法私有，getUnsafe 获取实例方法包私有，在包外只能通过反射获取
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);

        User test = new User();
        test.ttt = 12345;

        long SEED = unsafe.objectFieldOffset
                (User.class.getDeclaredField("ttt"));
        // 在 1.8 以后指针压缩是默认开启的，可以利用 -XX:-UseCompressedOops 参数进行关闭
        System.out.println(SEED);
        unsafe.putLong(test, SEED, 2333L);

        // this will cause exception
        // Unsafe.getUnsafe().putLong(test, 12L, 2333L);

        System.out.println(test.ttt);
    }

    private class User {
        public long ttt;
    }
}
