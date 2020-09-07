package com.liuliuliu.model.controller;

import com.liuliuliu.message.exception.MyCustomException;
import com.liuliuliu.model.dao.TestDao;
import com.liuliuliu.model.entity.Student;
import com.liuliuliu.model.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对类的描述
 *
 * @author liutianyang
 * @since 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TestService testService;

    @Autowired
    TestDao testDao;

    @GetMapping
    public String hello(){
        return "hello";
    }

    /**
     * 查询student
     * @param student
     * @return
     */
    @GetMapping("/student")
    public List<Student> queryStudent(Student student){
        return testDao.queryStudent(student);
    }

    @GetMapping("/me")
    public Object getCurrentUser(OAuth2Authentication principal){
        return principal;
    }

    @GetMapping("/test/exception")
    @Transactional
    public Object testException(){
        Student student = new Student();
        student.setId(1);
        student.setName("i小红");
        testDao.updateNameById(student);
        throw new MyCustomException("哈哈哈");

    }

    @GetMapping("/test2")
    public void test2(){

        System.out.println("111");
    }


}
