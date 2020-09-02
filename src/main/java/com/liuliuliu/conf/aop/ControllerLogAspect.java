package com.liuliuliu.conf.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 控制层日志
 */
@Component
@Aspect
//使用 order对多个切面进行排序,参数越小，越在前
@Order(1)
@Slf4j
public class ControllerLogAspect {

    private boolean logEnabled = true;

    // 匹配com.liuliuliu.model.controller包及子包下的任何方法执行
    @Pointcut(value="execution(* com.liuliuliu.model.controller..*.*(..))"
            + "&& !@target(com.liuliuliu.conf.annotation.NoLogAspect)")
    public void controllerLogPoint() {
    }

    @Around("controllerLogPoint()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        if (logEnabled) {
            Object result;
            String methodName = pjp.getSignature().getName();
            String declaringType = pjp.getSignature().getDeclaringTypeName();
            List<Object> args = new ArrayList<>(Arrays.asList(pjp.getArgs()));
            if (!args.isEmpty()) {
                for (int i = args.size() - 1; i >= 0; i--) {
                    if (args.get(i) != null && (args.get(i) instanceof OAuth2Authentication || args.get(i) instanceof MultipartHttpServletRequest)) {
                        args.remove(i);
                    }
                }
            }
            Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info( userId+ " " + declaringType + "." + methodName + " 开始. 参数: " + JSON.toJSON(args));
            long startTime = System.currentTimeMillis();
            result = pjp.proceed();
            long endTime = System.currentTimeMillis();
            log.info(userId + " " + declaringType + "." + methodName + " 结束. " + (endTime-startTime) + "ms");
            return result;
        } else {
            return pjp.proceed();
        }
    }

}
