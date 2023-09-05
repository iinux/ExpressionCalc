package cn.iinux.java.alpha.springboot.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalOnMissingBeanConfig {

    @Bean
    public A beanA(){
        return new A(); // 无条件定义一个bean : beanA
    }

    @Bean
    @ConditionalOnMissingBean(name="beanA")
    public B beanB(){
        // 如果 beanFactory 中存在一个名称为 beanA 的 bean，才定义bean ： beanB；
        // 因为上面的方法已经定义了一个 beanA，所以这里 beanB定义并不会发生。
        return new B();
    }

    @Bean
    @ConditionalOnMissingBean(name="beanD")
    public C beanC(){
        // 如果 beanFactory 中存在一个名称为 beanD的 bean，才定义bean ： beanC；
        return new C();
    }

}

class A {

}

class B {

}

class C {

}
