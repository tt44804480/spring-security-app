package com.liuliuliu.security.authentication;

import com.liuliuliu.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liuliuliu.security.validate.code.ValidateCodeFilter;
import com.liuliuliu.security.validate.code.ValidateSmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Locale;

/**
 * 资源服务器配置
 *
 * @author liutianyang
 * @since 1.0
 */
@Configuration
@EnableResourceServer
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {
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

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(validateSmsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/authentication/require")
                    .loginProcessingUrl("/authentication/form")
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

        resources.tokenServices(ImoocAuthorizationServerConfig.myTokenService);
    }

    /** 注册bean */
    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.CHINA);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        /**
         *	下面为加载自定义消息的properties文件，我放在了maven结构resources下，里面的提示消息可以自己定义，
         *	比如：密码错误是，原文件中提示的是，坏的凭证，我们可以找到它对应的key，修改它的值为 用户名或密码错误。
         */
        messageSource.addBasenames("classpath:messages_zh_CN");

        return messageSource;
    }

}
