package com.liuliuliu.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常类
 *
 * @author liutianyang
 * @since 1.0
 */
public class VolidateCodeException extends AuthenticationException{

    public VolidateCodeException(String msg){
        super(msg);
    }
}
