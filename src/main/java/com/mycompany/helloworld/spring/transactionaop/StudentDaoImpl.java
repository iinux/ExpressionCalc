package com.mycompany.helloworld.spring.transactionaop;

import com.mycompany.helloworld.spring.bean.Student;
import org.springframework.jdbc.core.JdbcTemplate;

public class StudentDaoImpl implements StudentDao {
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;
    @Override
    public void saveStudent(Student student) {
        String sql = "insert into student(name,age) values(?,?)";
        jdbcTemplate.update(sql, student.getName(), student.getAge());
    }
}
