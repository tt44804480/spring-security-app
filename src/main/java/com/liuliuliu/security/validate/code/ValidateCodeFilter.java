package com.liuliuliu.security.validate.code;

import com.liuliuliu.security.authentication.exception.VolidateCodeException;
import com.liuliuliu.security.constant.AuthorizationConstant;
import com.liuliuliu.security.validate.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码校验filter
 *
 * @author liutianyang
 * @since 1.0
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter{

    @Autowired
    @Qualifier("myAuthenticationFailureHandler")
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeRepository codeRepository;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(StringUtils.equals(AuthorizationConstant.USERNAME_PASSWORD_LOGIN_URI,request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")){
            try {
                ImageCode codeInSession = (ImageCode) codeRepository.get(request.getParameter("username"));
                String codeInRequest = request.getParameter("imageCode");
                if(StringUtils.isBlank(codeInRequest)){
                    throw new VolidateCodeException("验证码不能为空");
                }
                if(codeInSession == null){
                    throw new VolidateCodeException("验证码不存在");
                }
                if(codeInSession.expireIs()){
                    throw new VolidateCodeException("验证码已经过期");
                }
                if(!StringUtils.equals(codeInRequest, codeInSession.getCode())){
                    throw new VolidateCodeException("验证码不匹配");
                }
                codeRepository.remove(request.getParameter("username"));
                filterChain.doFilter(request, response);
            }catch (VolidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }

        }else{
            filterChain.doFilter(request, response);
        }
    }
}
