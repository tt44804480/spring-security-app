package com.liuliuliu.model.dao;

import com.liuliuliu.model.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao {

    List<Student> queryStudent(Student student);

    void updateNameById(Student student);
}
