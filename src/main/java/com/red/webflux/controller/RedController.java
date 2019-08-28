package com.red.webflux.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.result.UpdateResult;
import com.red.webflux.aop.annotation.Log;
import com.red.webflux.aop.enums.LogType;
import com.red.webflux.model.MongoLog;
import com.red.webflux.model.Red;
import com.red.webflux.service.PublisherService;
import com.red.webflux.service.RedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.parameters.P;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    @Qualifier("redis")
    private RedisTemplate redisTemplate;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/findAll")
    @Log(title = "查询", logType = LogType.SELECT)
    public Flux<Red> findAll() {
        return redService.findAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Log(title = "保存", logType = LogType.INSERT)
    public Mono<Red> create(@Valid @RequestBody Red blog) {
        log.info("create Blog with blog : {}", blog);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("red", JSON.toJSONString(blog));
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                log.info("发送消息成功");
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.info("失败发送:" + throwable.getMessage());
            }
        });
        return redService.createRed(blog);
    }

    @PutMapping("/{id}")
    @Log(title = "修改", logType = LogType.UPDATE)
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


    @Log(logType = LogType.OTHER)
    @GetMapping("/sendMsg")
    public String sendMsg() {
        publisherService.pushMsg("你好吗！！");
        return "success";
    }


    public static void main(String[] args) {

        Mono<String> stringMono = WebClient.builder().build()
                .method(HttpMethod.GET)
                .uri("http://www.baidu.com")
                .exchange()
                .flatMap(clientResponse -> {
                    return clientResponse.bodyToMono(String.class);
                }).onErrorMap(e -> new RuntimeException(e.getMessage(), e))
                .doOnSuccess(JSON -> {
                });

    }


    public String hello() {
        return "hello";
    }

}
