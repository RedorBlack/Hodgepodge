package com.red.webflux.service;


import com.github.pagehelper.PageInfo;
import com.mongodb.client.result.UpdateResult;
import com.red.webflux.model.JpaDemo;
import com.red.webflux.model.Red;
import com.red.webflux.model.RedDemo;
import com.red.webflux.mongo.PageResult;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    /**
     * 普通分页
     *
     * @param pageNum
     * @param pageLimit
     * @return
     */
    Page<JpaDemo> getPage(Integer pageNum, Integer pageLimit);

    /**
     * 排序分页
     *
     * @param pageNum
     * @param pageLimit
     * @return
     */
    Flux<List<JpaDemo>> getPageSort(Integer pageNum, Integer pageLimit);

    PageResult<Red> search();

    /**
     * 分页
     *
     * @return
     */
    PageInfo<T> find();
}
