package com.liuliuliu.security.authentication;

import com.liuliuliu.security.validate.jwtTokenStore.MyJwtAccessTokenConverter;
import com.liuliuliu.security.validate.jwtTokenStore.MyJwtTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
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

    //@Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory){
        return new  RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    public static class JwtTokenConfig{

        /*@Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey(SIGNING_KEY);
            return accessTokenConverter;
        }

        @Bean
        public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter){
            return new JwtTokenStore(jwtAccessTokenConverter);
        }*/

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

    }
}
