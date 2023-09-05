package cn.iinux.java.alpha.jvm;

// -Xss100k

public class StackOverflowTest {
    private int length;

    public void test() {
        length++;
        test();
    }

    public static void main(String[] args) {
        StackOverflowTest test = new StackOverflowTest();
        try {
            test.test();
        } catch (Throwable e) {
            System.out.println(test.length);
            e.printStackTrace();
        }
    }
}
