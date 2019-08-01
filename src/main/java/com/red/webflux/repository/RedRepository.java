package com.red.webflux.repository;

import com.red.webflux.model.Red;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:02 2019/8/1
 */
@Repository
public interface RedRepository extends ReactiveMongoRepository<Red, String> {

    Mono<Red> findByName(String name);

    Mono<Red> findByIdAndDeleteIsFalse(String id);


}
