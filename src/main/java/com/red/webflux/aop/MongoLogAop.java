package com.red.webflux.aop;

import com.red.webflux.model.MongoLog;
import com.red.webflux.service.RedService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Red
 * @Descpription: mongo 日志切面
 * @Date: 16:16 2019/8/21
 */
@Aspect
@Component
public class MongoLogAop {

    @Autowired
    private RedService redService;

    @Around("execution(* com.red.webflux.controller.*.*(..))")
    public Object aroundMod(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        MongoLog log = null;
        try {
            String targetName = proceedingJoinPoint.getTarget().getClass()
                    .getName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            System.out.println("方法名称：" + methodName);
            Object[] args = proceedingJoinPoint.getArgs();
            Class<?> targetClass = Class.forName(targetName);
            Object[] argsrequest = proceedingJoinPoint.getArgs();
            result = proceedingJoinPoint.proceed();
            log = new MongoLog();
            log.setCreateDate(System.currentTimeMillis());
            log.setCreatedTest("Red");
            log.setIp("0.0.0.0/0");
            log.setMethodName(methodName);
            log.setArguments(args);
            log.setTargetName(targetName);
            log.setResult(result);
            log.setTitle("请求日志");
        } catch (Throwable ex) {
            ex.printStackTrace();
            log.setResult(ex.getMessage());
            log.setTitle("请求出现异常");
        }finally {
            redService.insert(log);
        }
        return result;
    }


}
