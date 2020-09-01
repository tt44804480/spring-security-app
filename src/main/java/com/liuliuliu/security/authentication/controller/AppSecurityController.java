package com.liuliuliu.security.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 需要登录时的处理类
 *
 * @author liutianyang
 * @since 1.0
 */
@RestController
public class AppSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 需要身份认证时跳转到这里
     * @param request
     * @param response
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Object requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info(request.getParameter("username")+"登录.");

        return "请登录";

    }
}
