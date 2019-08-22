package com.red.webflux.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 10:00 2019/8/22
 */
public class ServletUtils {


    /**
     * 获取HttpServletRequest 线程安全类
     *
     * @return
     * @throws Exception
     */
    public static HttpServletRequest getRequest() throws Exception {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
