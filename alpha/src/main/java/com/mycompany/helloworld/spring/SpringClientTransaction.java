package com.mycompany.helloworld.spring;

import com.mycompany.helloworld.spring.bean.Student;
import com.mycompany.helloworld.spring.transactionaop.StudentService;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class SpringClientTransaction {
    public static void main(String[] args) {
        Resource resource = new ClassPathResource("applicationContext3.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        StudentService myService = (StudentService) factory.getBean("studentServiceProxy");
        Student student = new Student();
        student.setName("zhangsan");
        student.setAge(20);
        myService.saveStudent(student);
    }
}
