package com.liuliuliu.security.authentication.conf;

import com.liuliuliu.security.authentication.MyAccessDeniedHandler;
import com.liuliuliu.security.authentication.MyAuthenticationEntryPoint;
import com.liuliuliu.security.authentication.MyAuthenticationManager;
import com.liuliuliu.security.authentication.jwtTokenStore.MyResourceServerTokenServices;
import com.liuliuliu.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liuliuliu.security.constant.AuthorizationConstant;
import com.liuliuliu.security.validate.code.ValidateCodeFilter;
import com.liuliuliu.security.validate.code.ValidateSmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 资源服务器配置
 *
 * @author liutianyang
 * @since 1.0
 */
@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * 图形验证码过滤器
     */
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    /**
     * 短信验证码过滤器
     */
    @Autowired
    private ValidateSmsCodeFilter validateSmsCodeFilter;

    @Autowired
    @Qualifier("myAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Qualifier("myAuthenticationFailureHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

//    @Autowired
//    MyResourceServerTokenServices myResourceServerTokenServices;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(validateSmsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/authentication/require")
                    .loginProcessingUrl(AuthorizationConstant.USERNAME_PASSWORD_LOGIN_URI)
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                    .and()
                .authorizeRequests()
                    .antMatchers("/authentication/require",
                            "/code/*",
                            "/user/admin").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .csrf()
                    .disable()
                .apply(smsCodeAuthenticationSecurityConfig);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //自定义资源访问认证异常，没有token，或token错误，使用MyAuthenticationEntryPoint
        resources.authenticationEntryPoint(myAuthenticationEntryPoint);
        resources.accessDeniedHandler(myAccessDeniedHandler);

        resources.tokenServices(MyAuthorizationServerConfig.myResourceServerTokenServices);
        resources.authenticationManager(myAuthenticationManager());
    }


    private MyAuthenticationManager myAuthenticationManager(){
        MyAuthenticationManager myAuthenticationManager = new MyAuthenticationManager();
        myAuthenticationManager.setTokenServices(MyAuthorizationServerConfig.myResourceServerTokenServices);
        myAuthenticationManager.setClientDetailsService(MyAuthorizationServerConfig.myResourceServerTokenServices.getClientDetailsService());
        return myAuthenticationManager;
    }

}
