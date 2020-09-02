package com.liuliuliu.security.authentication.jwtTokenStore;

import com.liuliuliu.security.authentication.jwtTokenStore.MyJwtAccessTokenConverter;
import com.liuliuliu.security.authentication.jwtTokenStore.MyJwtTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置token的存储位置
 *
 * @author liutianyang
 * @since 1.0
 */
@Configuration
public class TokenStoreConfig {

    public static final String SIGNING_KEY = "liuliuliu";

    /**
     * 默认的springsecurity实现的token是uuid，并且是存放到内存中，项目重启token就丢失了。
     * 所以这里可以配置一个bean,将token存放到redis中
     * 但是也有另一个时髦的配置是--使用jwt。所以这个bean就不需要了
     * @param redisConnectionFactory
     * @return
     */
    //@Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory){
        return new  RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    public static class JwtTokenConfig{

        @Bean
        public MyJwtAccessTokenConverter myJwtAccessTokenConverter(){
            MyJwtAccessTokenConverter jwtAccessTokenConverter = new MyJwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(SIGNING_KEY);
            return jwtAccessTokenConverter;
        }

        @Bean
        public MyJwtTokenStore myJwtTokenStore(MyJwtAccessTokenConverter myJwtAccessTokenConverter){
            return new MyJwtTokenStore(myJwtAccessTokenConverter);
        }

        //@Bean
        public MyResourceServerTokenServices myResourceServerTokenServices(TokenStore myJwtTokenStore,
                                                                           ClientDetailsService clientDetailsService){
            MyResourceServerTokenServices myResourceServerTokenServices = new MyResourceServerTokenServices();
            myResourceServerTokenServices.setTokenStore(myJwtTokenStore);
            myResourceServerTokenServices.setClientDetailsService(clientDetailsService);

            return myResourceServerTokenServices;
        }

        //@Bean
        public MyAuthorizationServerTokenServices myAuthorizationServerTokenServices(TokenStore myJwtTokenStore,
                                                                                     TokenEnhancer myJwtAccessTokenConverter,
                                                                                     ClientDetailsService clientDetailsService){
            MyAuthorizationServerTokenServices myAuthorizationServerTokenServices = new MyAuthorizationServerTokenServices();
            myAuthorizationServerTokenServices.setTokenStore(myJwtTokenStore);
            myAuthorizationServerTokenServices.setTokenEnhancer(myJwtAccessTokenConverter);
            myAuthorizationServerTokenServices.setClientDetailsService(clientDetailsService);

            return myAuthorizationServerTokenServices;

        }

        //@Bean
        /*public MyTokenService myTokenService(TokenStore myJwtTokenStore,
                                                               ClientDetailsService clientDetailsService,
                                                               TokenEnhancer myJwtAccessTokenConverter,
                                                               AuthenticationManager authenticationManager){
            MyTokenService myTokenService = new MyTokenService();
            myTokenService.setTokenStore(myJwtTokenStore);
            myTokenService.setClientDetailsService(clientDetailsService);
            myTokenService.setTokenEnhancer(myJwtAccessTokenConverter);
            myTokenService.setAuthenticationManager(authenticationManager);

            return myTokenService;
        }*/

    }
}
