package com.liuliuliu.security.authentication.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 认证服务器的自定义异常，方便异常输出前端统一格式
 *
 * @Author lty
 * @Date 2019/11/15 11:48
 */
public class MyOAuth2Exception extends OAuth2Exception {

    public MyOAuth2Exception(String msg, Throwable t) {
        super(msg, t);

    }

    public MyOAuth2Exception(String msg) {
        super(msg);

    }
}
