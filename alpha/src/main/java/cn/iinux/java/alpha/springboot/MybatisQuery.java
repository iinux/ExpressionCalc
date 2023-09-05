package cn.iinux.java.alpha.springboot;

import cn.iinux.java.alpha.springboot.db.entity.Students;
import cn.iinux.java.alpha.springboot.db.entity.StudentsExample;
import cn.iinux.java.alpha.springboot.db.mapper.StudentsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
