package cn.iinux.java.alpha.jvm;

// jcmd pid VM.flags
// jcmd pid help
// jcmd pid help JFR.dump
// jcmd pid PerfCounter.print
// jcmd pid VM.uptime
// jcmd pid GC.class_histogram
// jcmd pid Thread.print
// jcmd pid GC.heap_dump <filename>
// jcmd pid VM.system_properties
// jcmd pid VM.version
// jcmd pid VM.command_line

// jstack jcmd jmap jstat jhat
// jconsole jvisualvm jmc
// java -cp $JAVA_HOME/lib/sa-jdi.jar sun.jvm.hotspot.HSDB
// jconsole remote: java -Djava.rmi.server.hostname=10.10.10.10 -Dcom.sun.management.jmxremote  -Dcom.sun.management.jmxremote.port=8999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false A

// jcmd
// jcmd <pid> JFR.start
// jcmd <pid> JFR.dump name=1 filename=recording.jfr
// jfr summary ~/recording.jfr

// oql
// select classof(classloader).name from instanceof java.lang.ClassLoader classloader

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
