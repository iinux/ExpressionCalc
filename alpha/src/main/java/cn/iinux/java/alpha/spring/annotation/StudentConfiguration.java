package cn.iinux.java.alpha.spring.annotation;

import cn.iinux.java.alpha.spring.bean.Student;
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
