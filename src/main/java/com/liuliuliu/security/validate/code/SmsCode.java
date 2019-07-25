package com.liuliuliu.security.validate.code;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 短信验证码类
 *
 * @author liutianyang
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class SmsCode {
    private String code;
    private LocalDateTime expireTime;

    public SmsCode(String code, LocalDateTime expireTime){
        this.code = code;
        this.expireTime = expireTime;
    }

    public SmsCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean expireIs(){
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}
