package com.red.webflux.config;

import com.red.webflux.mongo.MongoPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:49 2019/8/13
 */
@Configuration
public class MongoConfiguration {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public MongoPageHelper mongoPageHelper() {
        return new MongoPageHelper(mongoTemplate);
    }
}
