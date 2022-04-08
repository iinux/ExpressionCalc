package com.mycompany.helloworld.spring;

import com.mycompany.helloworld.spring.annotation.StudentConfiguration;
import com.mycompany.helloworld.spring.bean.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StringClientAnnotation {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(StudentConfiguration.class);
        context.refresh();
        Student student = (Student) context.getBean("student");
        System.out.println(student.getAge());
        System.out.println(student.getName());
    }
}
