package com.liuliuliu.security.authentication;

import com.liuliuliu.security.validate.jwtTokenStore.MyJwtAccessTokenConverter;
import com.liuliuliu.security.validate.jwtTokenStore.MyJwtTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

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
public class ImoocAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyJwtTokenStore tokenStore;

    @Autowired(required = false)
    private MyJwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private MyWebResponseExceptionTranslator myWebResponseExceptionTranslator;

    public static MyTokenService myTokenService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .exceptionTranslator(myWebResponseExceptionTranslator);
                //.tokenServices(createTokenService(endpoints));

        if(jwtAccessTokenConverter != null){
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
            tokenEnhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

            endpoints.accessTokenConverter(jwtAccessTokenConverter)
                        .tokenEnhancer(tokenEnhancerChain);
        }
        createTokenService(endpoints);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("liu")
                .secret("liuliuliu")
                //token的过期时间，0是永不过期
                .accessTokenValiditySeconds(3600)
                //refreshToken的过期时间
                .refreshTokenValiditySeconds(2592000)
                //liu 这个应用支持的授权模式
                .authorizedGrantTypes("refresh_token","password","mobile")
                //请求参数中的scope只可以是配置的值,类似权限
                .scopes("all","read","write");
    }

    private MyTokenService createTokenService(AuthorizationServerEndpointsConfigurer endpoints){
        MyTokenService myTokenService = new MyTokenService();

        myTokenService.setTokenStore(this.tokenStore);
        myTokenService.setClientDetailsService(endpoints.getClientDetailsService());
        myTokenService.setTokenEnhancer(endpoints.getTokenEnhancer());
        myTokenService.setAuthenticationManager(this.authenticationManager);

        ImoocAuthorizationServerConfig.myTokenService = myTokenService;
        return myTokenService;
    }
}
