package com.mycompany.helloworld.spring;

import com.mycompany.helloworld.spring.annotation.StudentConfiguration;
import com.mycompany.helloworld.spring.bean.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StringClientAnnotation {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(StudentConfiguration.class);
        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        StudentConfiguration configuration = (StudentConfiguration) context.getBean("studentConfiguration");
        Student student = (Student) context.getBean("student");

        System.out.println(configuration.getClass());
        System.out.println(student.getClass());
        System.out.println(student.getAge());
        System.out.println(student.getName());
    }
}
