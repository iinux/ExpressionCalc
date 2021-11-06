package com.mycompany.helloworld.jvm;

// jcmd pid VM.flags
// jcmd pid help
// jcmd pid help JFR.dump
// jcmd pid PerfCounter.print
// jcmd pid VM.uptime
// jcmd pid GC.class_histogram
// jcmd pid Thread.print
// jcmd pid GC.heap_dump <filename>
// jcmd pid VM.system_properties

public class DeadLockTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(A::methodA, "Thread-A").start();
        new Thread(B::methodB, "Thread-B").start();

        Thread.sleep(1000000);
    }
}

class A {
    public static synchronized void methodA() {
        System.out.println("methodA");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        B.methodB();
    }
}

class B {
    public static synchronized void methodB() {
        System.out.println("methodB");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        A.methodA();
    }
}