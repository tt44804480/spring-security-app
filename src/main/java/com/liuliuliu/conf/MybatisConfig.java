package com.liuliuliu.conf;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @author liutianyang
 * @create 2019-01-2019/1/19
 */
@MapperScan(value = "com.liuliuliu.model.dao")
@org.springframework.context.annotation.Configuration
public class MybatisConfig {

    /**
     * 配置mybatis
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {

            }
        };
    }
}
