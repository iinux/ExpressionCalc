package com.mycompany.helloworld.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.inject.Singleton;

@Singleton
class HelloPrinter {

    public void print() {
        System.out.println("Hello, World");
    }

}

@Singleton
public class Sample {
    @Inject
    private HelloPrinter printer;

    public void hello() {
        printer.print();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        Sample sample = injector.getInstance(Sample.class);
        sample.hello();
    }
}
