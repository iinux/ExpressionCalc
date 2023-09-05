package cn.iinux.java.alpha.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Names;

import javax.inject.Named;
import javax.inject.Singleton;

interface IHelloPrinter5 {
    void print();
}

@Singleton
class SimpleHelloPrinter5 implements IHelloPrinter5 {

    public void print() {
        System.out.println("Hello, Simple World");
    }

}

@Singleton
class ComplexHelloPrinter5 implements IHelloPrinter5 {

    public void print() {
        System.out.println("Hello, Complex World");
    }

}

class SampleModule5 extends AbstractModule {

    @Override
    protected void configure() {
        bind(IHelloPrinter5.class).annotatedWith(Names.named("simple")).to(SimpleHelloPrinter5.class);
        bind(IHelloPrinter5.class).annotatedWith(Names.named("complex")).to(ComplexHelloPrinter5.class);
    }

}

@Singleton
public class Sample5 {
    @Inject
    @Named("simple")
    private IHelloPrinter5 simplePrinter;

    @Inject
    @Named("complex")
    private IHelloPrinter5 complexPrinter;

    public void hello() {
        simplePrinter.print();
        complexPrinter.print();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SampleModule5());
        Sample5 sample = injector.getInstance(Sample5.class);
        sample.hello();
    }
}
