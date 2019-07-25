package com.liuliuliu.security.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 验证码登录 验证逻辑类
 *
 * @author liutianyang
 * @since 1.0
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticatioanToken = (SmsCodeAuthenticationToken) authentication;
        logger.info("手机号-验证码登录，使用自定义认证逻辑，手机号是：{}", authentication.getPrincipal());

        UserDetails userDetails = new User("username",
                "password",  false,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        SmsCodeAuthenticationToken authenticatioanRusult = new SmsCodeAuthenticationToken(userDetails,
                userDetails.getAuthorities());
        authenticatioanRusult.setDetails(authenticatioanToken.getDetails());
        return authenticatioanRusult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
