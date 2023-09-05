package cn.iinux.java.alpha.basic;

import lombok.Data;

public class ThreadExtendStudy {
    public static void main(String[] args) {
        MyThread thread = new MyThread(() -> {
            System.out.println(((MyThread) Thread.currentThread()).getMyField());
        });

        thread.setMyField(123);

        thread.start();
    }

    @Data
    static class MyThread extends Thread {
        long myField;

        MyThread(Runnable runnable) {
            super(runnable);
        }
    }
}
