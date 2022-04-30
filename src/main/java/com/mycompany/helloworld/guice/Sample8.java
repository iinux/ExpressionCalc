package com.mycompany.helloworld.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.MapBinder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

interface IHelloPrinter8 {
    void print();
}

@Singleton
class SimpleHelloPrinter8 implements IHelloPrinter8 {

    public void print() {
        System.out.println("Hello, Simple World");
    }

}

@Singleton
class ComplexHelloPrinter8 implements IHelloPrinter8 {

    public void print() {
        System.out.println("Hello, Complex World");
    }

}

class SampleModule8 extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<String, IHelloPrinter8> printers = MapBinder.newMapBinder(binder(), String.class, IHelloPrinter8.class);
        printers.addBinding("simple").to(SimpleHelloPrinter8.class);
        printers.addBinding("complex").to(ComplexHelloPrinter8.class);
    }

}

@Singleton
public class Sample8 {
    @Inject
    private Map<String, IHelloPrinter8> printers;

    public void hello() {
        for (String name : printers.keySet()) {
            printers.get(name).print();
        }
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SampleModule8());
        Sample8 sample = injector.getInstance(Sample8.class);
        sample.hello();
    }
}
