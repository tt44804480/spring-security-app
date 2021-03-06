package com.liuliuliu.security.authentication.userDetailsService;

import com.liuliuliu.security.db.service.OauthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * security的用户验证类
 *
 * @author liutianyang
 * @since 1.0
 */
@Component("usernamePasswordUserDetailsService")
public class UsernamePasswordUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OauthUserService oauthUserService;

    /**
     *  返回一个UserDetails类型的对象，交给springsecurity进行登录校验。
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = oauthUserService.getUserDetails(username);
        logger.info("用户：{}开始登录", userDetails.getUsername());
        return userDetails;
    }
}
