package com.red.webflux.service;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:12 2019/12/4
 */
@FunctionalInterface
public interface CheckedFunction<T,R> {

    /**
     * 重写方法 抛出异常
     * @param t
     * @return
     * @throws Exception
     */
    R apply(T t) throws Exception;
}
