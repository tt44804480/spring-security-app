package com.liuliuliu.message.handler;

import com.liuliuliu.message.exception.MyCustomException;
import com.liuliuliu.security.authentication.utils.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(MyCustomException.class)
    public RestResult handleException(RuntimeException e){

        RestResult restResult = new RestResult();
        restResult.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        restResult.setMsg(e.getMessage());
        restResult.setSince("myControllerAdvice");

        return restResult;
    }
}
