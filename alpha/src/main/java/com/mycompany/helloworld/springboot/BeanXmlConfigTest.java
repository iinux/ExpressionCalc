package com.mycompany.helloworld.springboot;

import com.mycompany.helloworld.spring.bean.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BeanXmlConfigTest {
    @Autowired
    Student student;

    @Test
    public void testConstructWorkerByXml() {
        System.out.println(student.getAge());
    }
}