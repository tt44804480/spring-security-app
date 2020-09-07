package com.liuliuliu.security.authentication.userDetailsService;

import com.liuliuliu.security.db.service.OauthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * security的用户验证类
 *
 * @author liutianyang
 * @since 1.0
 */

/**
 * 如果想用UsernamePasswordUserDetailsService进行默认的用户名密码登录 那么ioc容器中就不能存在2个以上的
 * UserDetailsService 类型的bean，
 * 因为 org.springframework.security.config.annotation.authentication.configuration.InitializeAuthenticationProviderBeanManagerConfigurer.InitializeUserDetailsManagerConfigurer#getBeanOrNull(java.lang.Class)
 * 这个方法会拿出来所有的UserDetailsService 类型的bean，判断如果大于一个  就返回默认的bean了  坑，待解决
 */
@Component("userIdUserDetailsService")
public class UserIdUserDetailsService
        //implements UserDetailsService
{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OauthUserService oauthUserService;

    /**
     *  返回一个UserDetails类型的对象，刷新token时候使用
     * @param userId userid
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    //@Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserDetails userDetails = oauthUserService.getUserDetails(Long.valueOf(userId));
        logger.info("用户：{}开始登录", userDetails.getUsername());
        return userDetails;
    }
}
