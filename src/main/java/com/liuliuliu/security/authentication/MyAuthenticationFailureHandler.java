package com.liuliuliu.security.authentication;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录失败处理类
 *
 * @author liutianyang
 * @since 1.0
 */
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e)
            throws IOException, ServletException {
        logger.info("登录失败");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", "xxx");
        result.put("msg", e.getMessage());
        result.put("data", null);
        /**
         * 处理对应的异常响应
         */
        /*if(e instanceof BadCredentialsException){
            httpServletResponse.getWriter().write("帐号或密码错误");
        }else if(e instanceof LockedException){
            httpServletResponse.getWriter().write("用户已锁定");
        }else if(e instanceof DisabledException){
            httpServletResponse.getWriter().write("用户不存在");
        }else{
            httpServletResponse.getWriter().write(e.getLocalizedMessage());
        }*/

        httpServletResponse.getWriter().write(JSONObject.toJSONString(result));
    }
}
