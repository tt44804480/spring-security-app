package com.liuliuliu.security.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 对类的描述
 *
 * @author liutianyang
 * @since 1.0
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void send(String key, Object value) {
        redisTemplate.opsForValue().set(buildKey(key), value, 20, TimeUnit.MINUTES);
        logger.info("发送验证码..{}", value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(buildKey(key));
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 构建 存储验证码的key,格式为 validate:code:[username]
     * @return
     */
    private String buildKey(String username){
        String keyPrex = "validate:code:";
        return keyPrex+username;
    }
}
