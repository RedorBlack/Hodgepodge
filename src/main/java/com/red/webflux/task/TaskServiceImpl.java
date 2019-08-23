package com.red.webflux.task;

import com.red.webflux.model.MongoLog;
import com.red.webflux.service.RedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 11:17 2019/8/22
 */
@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private RedService redService;

    @Override
    public void doTask(MongoLog mongoLog) {
        redService.insert(mongoLog);
    }
}
