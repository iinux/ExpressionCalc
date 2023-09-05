package com.mycompany.helloworld.spring.transactionaop;

import com.mycompany.helloworld.spring.bean.Student;

public class StudentServiceImpl implements StudentService {
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    StudentDao studentDao;
    @Override
    public void saveStudent(Student student) {
        studentDao.saveStudent(student);
        throw new RuntimeException("i am exception");
    }
}
