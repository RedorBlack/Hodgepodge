package com.red.webflux.repository;

import com.red.webflux.model.MongoLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:36 2019/8/21
 */
@Repository
public interface LogRepository extends ReactiveMongoRepository<MongoLog, String> {
}
