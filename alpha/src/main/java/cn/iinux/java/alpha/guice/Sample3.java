package cn.iinux.java.alpha.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

class HelloPrinter3 {

    private static int counter;

    private final int myCounter;

    public HelloPrinter3() {
        myCounter = counter++;
    }

    public void print() {
        System.out.printf("Hello, World %d\n", myCounter);
    }

}

public class Sample3 {
    @Inject
    private HelloPrinter3 printer;

    public void hello() {
        printer.print();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        Sample3 sample = injector.getInstance(Sample3.class);
        sample.hello();
        sample = injector.getInstance(Sample3.class);
        sample.hello();
        sample = injector.getInstance(Sample3.class);
        sample.hello();
        sample = injector.getInstance(Sample3.class);
        sample.hello();
    }
}
