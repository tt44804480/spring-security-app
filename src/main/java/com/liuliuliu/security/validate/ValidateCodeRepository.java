package com.liuliuliu.security.validate;

/**
 * 验证码操作接口
 */
public interface ValidateCodeRepository {

    /**
     * 发送验证码
     */
    void send(String key, Object value);

    /**
     * 获取验证码
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除验证码
     * @param key
     */
    void remove(String key);
}
