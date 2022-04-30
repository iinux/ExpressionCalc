package com.mycompany.helloworld.guice;

import com.google.inject.Guice;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.inject.Singleton;

@ImplementedBy(SimpleHelloPrinter.class)
interface IHelloPrinter {
    void print();
}

@Singleton
class SimpleHelloPrinter implements IHelloPrinter {

    public void print() {
        System.out.println("Hello, Simple World");
    }

}

@Singleton
class ComplexHelloPrinter implements IHelloPrinter {

    public void print() {
        System.out.println("Hello, Complex World");
    }

}

@Singleton
public class Sample2 {
    @Inject
    private IHelloPrinter printer;

    public void hello() {
        printer.print();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        Sample2 sample = injector.getInstance(Sample2.class);
        sample.hello();
    }
}
