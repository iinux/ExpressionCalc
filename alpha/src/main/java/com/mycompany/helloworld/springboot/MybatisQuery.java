package com.mycompany.helloworld.springboot;

import com.mycompany.helloworld.springboot.db.entity.Students;
import com.mycompany.helloworld.springboot.db.entity.StudentsExample;
import com.mycompany.helloworld.springboot.db.mapper.StudentsMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisQuery {

    @Autowired
    StudentsMapper studentsMapper;

    @Test
    public void query() {
        StudentsExample example = new StudentsExample();
        example.createCriteria().andNameEqualTo("AA");
        List<Students> result = studentsMapper.selectByExample(example);
        System.out.println(result);

    }
}
