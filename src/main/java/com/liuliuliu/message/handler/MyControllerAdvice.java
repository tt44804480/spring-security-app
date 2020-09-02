package com.liuliuliu.message.handler;

import com.liuliuliu.message.exception.MyCustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyControllerAdvice {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(MyCustomException.class)
    public Map<String, Object> handleException(RuntimeException e){

        Map<String,Object> map = new HashMap<>();

        map.put("code", "500");
        map.put("msg", e.getMessage());

        return map;
    }
}
