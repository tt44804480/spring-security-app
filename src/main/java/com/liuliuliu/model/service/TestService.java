package com.liuliuliu.model.service;

import com.liuliuliu.model.dao.TestDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 对类的描述
 *
 * @author liutianyang
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class TestService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TestDao testDao;

}
