package com.red.webflux.controller;

import com.mongodb.client.result.UpdateResult;
import com.red.webflux.model.Red;
import com.red.webflux.service.RedService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

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

    @GetMapping
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
}