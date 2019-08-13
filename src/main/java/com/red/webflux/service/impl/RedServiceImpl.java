package com.red.webflux.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.red.webflux.model.Red;
import com.red.webflux.repository.RedRepository;
import com.red.webflux.service.RedService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:08 2019/8/1
 */
@Service
public class RedServiceImpl implements RedService {


    @Autowired
    private RedRepository redRepository;


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Mono<Red> createRed(Red red) {
        return redRepository.insert(red);
    }

    @Override
    public Flux<Red> findAll() {
        return redRepository.findAll();
    }

    @Override
    public Mono<Red> findById(String id) {
        return redRepository.findById(id);
    }

    @Override
    public Mono<Red> findByName(String name) {
        return redRepository.findByName(name).switchIfEmpty(Mono.error(new Exception("没有查询到此纪录")));
    }

    @Override
    public Mono<Red> update(Red red, String id) {
        return redRepository.findById(id).doOnSuccess(red1 -> {

            red1.setAge(red.getAge());
            red1.setName(red.getName());
            red1.setMessage(red.getMessage());
            redRepository.save(red1).subscribe();
        });
    }

    @Override
    public Mono<UpdateResult> updateByName(String[] ids, String name) {
        List<ObjectId> list = Arrays.asList(ids).stream().map(s -> {
            ObjectId objectId = new ObjectId(s);
            return objectId;
        }).collect(Collectors.toList());
        Query query = Query.query(Criteria.where("id").in(list));
        return Mono.just(mongoTemplate.updateMulti(query, Update.update("red_name", name), Red.class)).doOnError(throwable -> {
            Mono.error(throwable);
        });
    }

    @Override
    public Mono<Red> findByIdAndDeleteIsFalse(String id) {
        return redRepository.findByIdAndDeleteIsFalse(id).switchIfEmpty(Mono.error(new Exception("没有查找到记录:" + id)));
    }
}