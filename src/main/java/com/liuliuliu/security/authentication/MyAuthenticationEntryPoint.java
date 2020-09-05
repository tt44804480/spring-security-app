package com.liuliuliu.security.authentication;

import com.alibaba.fastjson.JSONObject;
import com.liuliuliu.security.authentication.utils.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理没有token，或token错误,或token过期
 * 认证异常
 *
 * @Author lty
 * @Date 2019/11/15 11:16
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        RestResult restResult = new RestResult();
        restResult.setCode(HttpStatus.UNAUTHORIZED.value());
        restResult.setMsg(authException.getMessage());
        restResult.setSince("myAccessDeniedHandler");

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(restResult));
    }
}
