package com.red.webflux.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:28 2019/8/27
 */
@Component
public class RedisService {

    @Autowired
    @Qualifier("redis")
   private RedisTemplate redisTemplate;



    @Autowired
    private CountDownLatch latch;


    /**
     * 向通道发送消息的方法
     * @param channel
     * @param message
     */
    public void sendChannelMess(String channel, String message) {
        try {
            redisTemplate.convertAndSend(channel, message);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
