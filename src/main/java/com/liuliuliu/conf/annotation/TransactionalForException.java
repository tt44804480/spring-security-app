package com.liuliuliu.conf.annotation;


import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对所有异常进行roll back
 *
 * @author Liuty
 * @since 0.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(rollbackFor=Exception.class)
public @interface TransactionalForException {
}
