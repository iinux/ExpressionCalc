package com.mycompany.helloworld.spring.annotation;

import com.mycompany.helloworld.spring.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfiguration {
    @Bean(name = "student")
    public Student getStudent() {
        Student student = new Student();
        student.setName("zhangsan");
        student.setAge(21);
        return student;
    }
}
