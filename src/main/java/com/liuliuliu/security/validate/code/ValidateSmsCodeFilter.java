package com.liuliuliu.security.validate.code;

import com.liuliuliu.security.validate.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 短信验证码校验类
 *
 * @author liutianyang
 * @since 1.0
 */
@Component("validateSmsCodeFilter")
public class ValidateSmsCodeFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("myAuthenticationFailureHandler")
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeRepository codeRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(StringUtils.equals("/authentication/mobile",request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")){
            /**
             * 请求中要带
             * 请求头：Authorization
             *  body中：grant_type
             */
            try {
                SmsCode codeInSession = (SmsCode) codeRepository.get(request.getParameter("mobile"));
                String codeInRequest = request.getParameter("smsCode");
                if(StringUtils.isBlank(codeInRequest)){
                    throw new VolidateCodeException("手机验证码不能为空");
                }
                if(codeInSession == null){
                    throw new VolidateCodeException("手机验证码不存在");
                }
                if(codeInSession.expireIs()){
                    throw new VolidateCodeException("手机验证码已经过期");
                }
                if(!StringUtils.equals(codeInRequest, codeInSession.getCode())){
                    throw new VolidateCodeException("手机验证码不匹配");
                }
                codeRepository.remove(request.getParameter("mobile"));
                filterChain.doFilter(request, response);
            }catch (VolidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }

        }else{
            filterChain.doFilter(request, response);
        }
    }
}
