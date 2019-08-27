package com.red.webflux.service.impl;

import com.red.webflux.redis.Const;
import com.red.webflux.redis.RedisService;
import com.red.webflux.service.PublisherService;
import com.red.webflux.service.RedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:31 2019/8/27
 */
@Service
public class PublisherServiceImpl implements PublisherService {


    @Autowired
    private RedisService redisService;


    @Autowired
    @Qualifier("redis")
    private RedisTemplate redisTemplate;

    @Override
    public String pushMsg(String params) {
        redisService.sendChannelMess(Const.CHANNEL, "我发信息了哦");
        redisTemplate.opsForValue().set("msg", "我发信息了哦");
        return "success";
    }


}
