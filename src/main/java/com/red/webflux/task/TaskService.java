package com.red.webflux.task;

import com.red.webflux.model.MongoLog;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 11:17 2019/8/22
 */
public interface TaskService {

    /**
     * 执行任务
     */
    void doTask(MongoLog mongoLog);
}
