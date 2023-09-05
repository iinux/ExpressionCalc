package com.mycompany.helloworld.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.inject.Singleton;

interface IHelloPrinter4 {
    void print();
}

@Singleton
class SimpleHelloPrinter4 implements IHelloPrinter4 {

    public void print() {
        System.out.println("Hello, Simple World");
    }

}

@Singleton
class ComplexHelloPrinter4 implements IHelloPrinter4 {

    public void print() {
        System.out.println("Hello, Complex World");
    }

}

class SampleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IHelloPrinter4.class).to(SimpleHelloPrinter4.class);
    }

}

@Singleton
public class Sample4 {
    @Inject
    private IHelloPrinter4 printer;

    public void hello() {
        printer.print();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SampleModule());
        Sample4 sample = injector.getInstance(Sample4.class);
        sample.hello();
    }
}
