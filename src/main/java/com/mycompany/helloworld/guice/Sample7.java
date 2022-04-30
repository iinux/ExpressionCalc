package com.mycompany.helloworld.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

interface IHelloPrinter7 {
    void print();
}

@Singleton
class SimpleHelloPrinter7 implements IHelloPrinter7 {

    public void print() {
        System.out.println("Hello, Simple World");
    }

}

@Singleton
class ComplexHelloPrinter7 implements IHelloPrinter7 {

    public void print() {
        System.out.println("Hello, Complex World");
    }

}

class SampleModule7 extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<IHelloPrinter7> printers = Multibinder.newSetBinder(binder(), IHelloPrinter7.class);
        printers.addBinding().to(SimpleHelloPrinter7.class);
        printers.addBinding().to(ComplexHelloPrinter7.class);
    }

}

@Singleton
public class Sample7 {
    @Inject
    private Set<IHelloPrinter7> printers;

    public void hello() {
        for (IHelloPrinter7 printer : printers) {
            printer.print();
        }
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SampleModule7());
        Sample7 sample = injector.getInstance(Sample7.class);
        sample.hello();
    }
}
