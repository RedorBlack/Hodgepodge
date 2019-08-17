package com.red.webflux.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.result.UpdateResult;
import com.red.webflux.repository.JpaDemoRepository;
import com.red.webflux.model.JpaDemo;
import com.red.webflux.model.Red;
import com.red.webflux.mongo.PageResult;
import com.red.webflux.service.RedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:14 2019/8/1
 */
@Slf4j
@RestController
@RequestMapping("/red/api")
public class RedController {


    @Autowired
    private RedService redService;


    @Autowired
    private JpaDemoRepository jpaDemoRepository;

    @GetMapping
    @SentinelResource("findAll")
    public Flux<Red> findAll() {
        log.info("findAll Blog");
        return redService.findAll();

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Red> create(@Valid @RequestBody Red blog) {
        log.info("create Blog with blog : {}", blog);
        return redService.createRed(blog);
    }

    @PutMapping("/{id}")
    public Mono<Red> updateBlog(@RequestBody Red blog, @PathVariable String id) {
        log.info("updateBlog Blog with id : {} and blog : {}", id, blog);
        return redService.update(blog, id);
    }

    @GetMapping("/{id}")
    public Mono<Red> findByIdAndDeleteIsFalse(@PathVariable String id) {
        return redService.findByIdAndDeleteIsFalse(id);
    }

    @PostMapping("/update")
    public Mono<UpdateResult> updateByName(@RequestParam("name") String name, @RequestParam("ids") String[] ids) {
        return redService.updateByName(ids, name);
    }

    @GetMapping("/search")
    PageResult<Red> search() {
        return redService.search();
    }


    @GetMapping("/findQuerry")
    Flux<List<JpaDemo>> findQuerry() {
        return redService.getPageSort(10, 20);
    }

    @GetMapping("/findAll")
    Page<JpaDemo> find() {
        ArrayList arrayList = new ArrayList<>();
        for (; ; ) {
            JpaDemo jpaDemo = new JpaDemo();
            jpaDemo.setAge(100);
            jpaDemo.setMsg("haha");
            jpaDemo.setName("hehe");
            arrayList.add(jpaDemo);

            if (arrayList.size() == 50) {
                break;
            }
        }
        jpaDemoRepository.saveAll(arrayList);
        return redService.getPage(1, 30);
    }

    @GetMapping("/findPage")
    PageInfo findPage() {
        return redService.find();

    }


}
