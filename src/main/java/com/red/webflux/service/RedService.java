package com.red.webflux.service;

import com.mongodb.client.result.UpdateResult;
import com.red.webflux.model.Red;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:06 2019/8/1
 */
public interface RedService {


    Mono<Red> createRed(Red red);

    Flux<Red> findAll();

    Mono<Red> findById(String id);

    Mono<Red> findByName(String name);

    Mono<Red> update(Red red, String id);

    Mono<UpdateResult> updateByName(String[] ids, String name);

    Mono<Red> findByIdAndDeleteIsFalse(String id);
}
