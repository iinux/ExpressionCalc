package com.mycompany.helloworld.spring;

import com.mycompany.helloworld.spring.aop.MyService;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class SpringClientAOP {
    public static void main(String[] args) {
        Resource resource = new ClassPathResource("applicationContext2.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        MyService myService = (MyService) factory.getBean("myAOP");
        myService.myMethod();

        System.out.println(myService.getClass());
        System.out.println(myService.getClass().getSuperclass());
        System.out.println(myService.getClass().getInterfaces().length);
        for (Class<?> anInterface : myService.getClass().getInterfaces()) {
            System.out.println(anInterface);
        }
    }
}
