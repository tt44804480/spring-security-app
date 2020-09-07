package com.liuliuliu.security.authentication.conf;

import com.liuliuliu.security.authentication.jwtTokenStore.*;
import com.liuliuliu.security.authentication.MyWebResponseExceptionTranslator;
import com.liuliuliu.security.authentication.userDetailsService.UserIdUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 *
 * @author liutianyang
 * @since 1.0
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService usernamePasswordUserDetailsService;

    @Autowired
    private MyJwtTokenStore tokenStore;

    @Autowired(required = false)
    private MyJwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private MyWebResponseExceptionTranslator myWebResponseExceptionTranslator;

    @Autowired
    private UserIdUserDetailsService userIdUserDetailsService;



    public static MyAuthorizationServerTokenServices myAuthorizationServerTokenServices;

    public static MyResourceServerTokenServices myResourceServerTokenServices;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(usernamePasswordUserDetailsService)
                .exceptionTranslator(myWebResponseExceptionTranslator)
                ;

        if(jwtAccessTokenConverter != null){
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
            tokenEnhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

            endpoints.accessTokenConverter(jwtAccessTokenConverter)
                        .tokenEnhancer(tokenEnhancerChain);
        }


        createTokenService(endpoints);
        myResourceServerTokenServices(endpoints);

        endpoints.tokenServices(myAuthorizationServerTokenServices);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("liu")
                .secret("liuliuliu")
                //token的过期时间，0是永不过期
                .accessTokenValiditySeconds(36000)//1个小时
                //refreshToken的过期时间
                .refreshTokenValiditySeconds(2592000)//1个月
                //liu 这个应用支持的授权模式
                .authorizedGrantTypes("refresh_token","password","mobile")
                //请求参数中的scope只可以是配置的值,类似权限
                .scopes("all","read","write");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    }




    private MyAuthorizationServerTokenServices createTokenService(AuthorizationServerEndpointsConfigurer endpoints){
        MyAuthorizationServerTokenServices myAuthorizationServerTokenServices = new MyAuthorizationServerTokenServices();
        myAuthorizationServerTokenServices.setTokenStore(this.tokenStore);
        myAuthorizationServerTokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        myAuthorizationServerTokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        myAuthorizationServerTokenServices.setAuthenticationManager(authenticationManager);
        myAuthorizationServerTokenServices.setSupportRefreshToken(true);
        myAuthorizationServerTokenServices.setUserIdUserDetailsService(userIdUserDetailsService);

        MyAuthorizationServerConfig.myAuthorizationServerTokenServices = myAuthorizationServerTokenServices;
        return myAuthorizationServerTokenServices;
    }

    public MyResourceServerTokenServices myResourceServerTokenServices(AuthorizationServerEndpointsConfigurer endpoints){
        MyResourceServerTokenServices myResourceServerTokenServices = new MyResourceServerTokenServices();
        myResourceServerTokenServices.setTokenStore(this.tokenStore);
        myResourceServerTokenServices.setClientDetailsService(endpoints.getClientDetailsService());

        MyAuthorizationServerConfig.myResourceServerTokenServices = myResourceServerTokenServices;
        return myResourceServerTokenServices;
    }
}
