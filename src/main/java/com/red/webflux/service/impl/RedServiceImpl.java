package com.red.webflux.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.result.UpdateResult;
import com.red.webflux.model.MongoLog;
import com.red.webflux.repository.JpaDemoRepository;
import com.red.webflux.dao.RedDemoMapper;
import com.red.webflux.model.JpaDemo;
import com.red.webflux.model.Red;
import com.red.webflux.model.RedDemo;
import com.red.webflux.mongo.MongoPageHelper;
import com.red.webflux.mongo.PageResult;
import com.red.webflux.repository.LogRepository;
import com.red.webflux.repository.RedRepository;
import com.red.webflux.service.RedService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
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
@Slf4j
public class RedServiceImpl implements RedService {


    @Autowired
    private RedDemoMapper redDemoMapper;
    @Autowired
    private RedRepository redRepository;

    @Autowired
    private MongoPageHelper mongoPageHelper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private LogRepository logRepository;

    /**
     * jpa 分页
     */
    @Autowired
    private JpaDemoRepository jpaDemoRepository;

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

    /**
     * jpa 分页
     *
     * @param pageNum
     * @param pageLimit
     * @return
     */
    @Override
    public Page<JpaDemo> getPage(Integer pageNum, Integer pageLimit) {
        return jpaDemoRepository.findAll(PageRequest.of(pageNum, pageLimit, Sort.by("id")));
    }

    @Override
    public Flux<List<JpaDemo>> getPageSort(Integer pageNum, Integer pageLimit) {
        return null;
    }


    @Override
    public PageResult<Red> search() {
        Query query = Query.query(Criteria.where("version").is(0));
        return mongoPageHelper.pageQuery(query, Red.class, 3, 1, a -> a, null);
//        mongoPageHelper.pageQuery(query, Red.class, 3, 1);
    }


    @Override
    public Flux<PageInfo> querry(Integer pageNum, Integer pageSize) {
        if (pageNum == null && pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null && pageSize == 0) {
            pageSize = 20;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<RedDemo> redDemos = redDemoMapper.findAll();
        PageInfo pageInfo = new PageInfo<>(redDemos);
        return Flux.just(pageInfo).doOnError(throwable -> {
            Flux.error(throwable);
        });
    }


    /**
     * 异步加载
     * @param mongoLog
     */
    @Override
    public void insert(MongoLog mongoLog) {
        mongoTemplate.insert(mongoLog, "mongo_logs");
    }

    @Override
    public Flux<MongoLog> findLogs() {
        return logRepository.findAll();
    }
}
