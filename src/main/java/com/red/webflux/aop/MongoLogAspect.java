package com.red.webflux.aop;

import com.red.webflux.aop.annotation.Log;
import com.red.webflux.app.ApplicationContextProvider;
import com.red.webflux.model.MongoLog;
import com.red.webflux.service.RedService;
import com.red.webflux.util.IpUtils;
import com.red.webflux.util.ServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: Red
 * @Descpription: mongo 日志切面
 * @Date: 16:16 2019/8/21
 */
@Aspect
@Component
public class MongoLogAspect {


    /**
     * 切点
     */
    @Pointcut("@annotation(com.red.webflux.aop.annotation.Log)")
    public void logPointCut() {

    }

    /**
     * 后置通知
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null);

    }

    /**
     * 拦截异常操作
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }


    /**
     * 保存日志
     */
    protected void handleLog(final JoinPoint joinPoint, final Exception e) {

        try {
            // 方法注解
            Log methodAnnotationLog = getMethodAnnotationLog(joinPoint);
            if (methodAnnotationLog == null) {
                return;
            }
            // 类注解
            Log clazzAnnotationLog = getClazzAnnotationLog(joinPoint);
            MongoLog log = new MongoLog();
            final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
            String os = userAgent.getOperatingSystem().getName();
            log.setOs(os);
            log.setBrowser(userAgent.getBrowser().getName());
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            log.setOperationIp(ip);
            log.setRequestUri(ServletUtils.getRequest().getRequestURI());
            if (e != null) {
                log.setStatus("-1");
                log.setMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            log.setMethod(methodName);
            log.setTargetName(className);
            log.setLogType(methodAnnotationLog.logType().name());
            if (clazzAnnotationLog != null && StringUtils.isNotBlank(clazzAnnotationLog.title())) {
                log.setTitle(methodAnnotationLog.title());
            } else {
                log.setTitle(clazzAnnotationLog.title());
            }
            if (methodAnnotationLog.requestParam()) {
                log.setParams(ServletUtils.getRequest().getParameterMap());
            }
            log.setArgs(joinPoint.getArgs());
            /**
             * 异步保存
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApplicationContextProvider.getBean(RedService.class).insert(log);
                }
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getMethodAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 是否类注解，如果存在就获取
     */
    private Log getClazzAnnotationLog(JoinPoint joinPoint) throws Exception {
        Class clazz = joinPoint.getTarget().getClass();
        return (Log) clazz.getAnnotation(Log.class);
    }


}
