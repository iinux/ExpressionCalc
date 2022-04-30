package com.mycompany.helloworld.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Names;

import javax.inject.Named;
import javax.inject.Singleton;

interface IHelloPrinter6 {
    void print();
}

@Singleton
class SimpleHelloPrinter6 implements IHelloPrinter6 {

    public void print() {
        System.out.println("Hello, Simple World");
    }

}

@Singleton
class ComplexHelloPrinter6 implements IHelloPrinter6 {

    public void print() {
        System.out.println("Hello, Complex World");
    }

}

class SampleModule6 extends AbstractModule {

    @Override
    protected void configure() {
        bind(IHelloPrinter6.class).annotatedWith(Names.named("simple")).to(SimpleHelloPrinter6.class);
        bind(IHelloPrinter6.class).annotatedWith(Names.named("complex")).to(ComplexHelloPrinter6.class);
    }

}

@Singleton
public class Sample6 {
    // @Named("simple")
    private IHelloPrinter6 simplePrinter;

    // @Named("complex")
    private IHelloPrinter6 complexPrinter;

    @Inject
    public Sample6(@Named("simple") IHelloPrinter6 simplePrinter, @Named("complex") IHelloPrinter6 complexPrinter) {
        this.simplePrinter = simplePrinter;
        this.complexPrinter = complexPrinter;
    }

    public void hello() {
        simplePrinter.print();
        complexPrinter.print();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SampleModule6());
        Sample6 sample = injector.getInstance(Sample6.class);
        sample.hello();
    }
}
