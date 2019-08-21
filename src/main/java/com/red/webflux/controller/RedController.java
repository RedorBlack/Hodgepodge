package com.red.webflux.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.result.UpdateResult;
import com.red.webflux.aop.annotation.Log;
import com.red.webflux.aop.enums.LogType;
import com.red.webflux.model.MongoLog;
import com.red.webflux.model.Red;
import com.red.webflux.service.RedService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:14 2019/8/1
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Log(title = "mongo")
public class RedController {

    @Autowired
    private RedService redService;

    @GetMapping
    @Log(logType = LogType.SELECT)
    @SentinelResource("findAll")
    public Flux<Red> findAll(HttpServletRequest request, HttpServletResponse response) {
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

    @GetMapping("/querry")
    public Flux<PageInfo> querry() {
        return redService.querry(1, 20);
    }


    @Log(logType = LogType.SELECT)
    @GetMapping("/findLogs")
    public Flux<MongoLog> findLogs() {
        return redService.findLogs();
    }

}
