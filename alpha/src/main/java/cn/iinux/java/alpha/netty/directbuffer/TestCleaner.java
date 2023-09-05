package cn.iinux.java.alpha.netty.directbuffer;

public class TestCleaner {
    static class Obj {

    }
    public static void main(String[] args) {
        call();
        System.gc();
    }

    protected static void call() {
        Obj obj = new Obj();

        /*
        Cleaner.create(obj, () -> {
            System.out.println("cleaner run");
        });

         */
        MyOwnCleaner.clear(obj, () -> {
            System.out.println("cleaner run");
        });

    }
}
