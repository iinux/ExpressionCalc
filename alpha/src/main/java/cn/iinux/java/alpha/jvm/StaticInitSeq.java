package cn.iinux.java.alpha.jvm;

public class StaticInitSeq {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getSingleton();
        System.out.println(Singleton.counter1);
        System.out.println(Singleton.counter2);
    }
}

class Singleton {
    public static int counter1;

    private static final Singleton singleton = new Singleton();

    private Singleton() {
        counter1++;
        counter2++;

        System.out.println(counter1);
        System.out.println(counter2);
    }

    public static int counter2 = 0;

    public static Singleton getSingleton() {
        return singleton;
    }
}
