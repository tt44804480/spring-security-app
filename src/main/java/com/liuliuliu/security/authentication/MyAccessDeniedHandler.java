package com.liuliuliu.security.authentication;

import com.alibaba.fastjson.JSONObject;
import com.liuliuliu.security.authentication.utils.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义资源服务器异常处理
 * 无权限
 *
 * @Author lty
 * @Date 2019/11/15 11:17
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        RestResult restResult = new RestResult();
        restResult.setCode(HttpStatus.BAD_REQUEST.value());
        restResult.setMsg(accessDeniedException.getMessage());
        restResult.setSince("myAccessDeniedHandler");

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(restResult));

       /* try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), restResult);
        } catch (Exception e) {
            throw new ServletException();
        }*/
    }
}
