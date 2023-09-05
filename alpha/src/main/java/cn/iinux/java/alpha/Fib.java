package cn.iinux.java.alpha;

public class Fib {
    void test(GrandPa grandPa) {
        System.out.println("grandpa");
    }

    void test(Father father) {
        System.out.println("father");
    }

    void test(Son son) {
        System.out.println("son");
    }

    public static void main(String[] args) {
        Fib fib = new Fib();
        GrandPa g1 = new Father();
        GrandPa g2 = new Son();
        fib.test(g1);
        fib.test((Son)g2);
    }
}

class GrandPa {

}

class Father extends GrandPa {

}

class Son extends Father {

}
