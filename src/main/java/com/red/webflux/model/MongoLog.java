package com.red.webflux.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Map;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 15:42 2019/8/21
 */
@Document(collection = "mongo_logs")
@Data
public class MongoLog extends BaseEntity {

    @Field("title")
    private String title;
    @Field("create_date")
    private Long createDate;
    @Field("created_Tester")
    private String createdTest;
    @Field("arguments")
    private Object[] arguments;
    @Field("target_Name")
    private String targetName;
    @Field("method_Name")
    private String methodName;
    private Object result;
    /**
     * 日志内容
     */
    @Field(value = "content")
    private String content;
    /**
     * 操作方式
     */
    @Field(value = "log_type")
    private String logType;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 浏览器
     */
    @Field(value = "browser")
    private String browser;
    /**
     * 操作系统
     */
    @Field(value = "os")
    private String os;
    /**
     * 请求URI
     */
    @Field(value = "operation_ip")
    private String operationIp;
    @Field(value = "operation_name")
    private String operationName;
    /**
     * 操作方法
     */
    @Field(value = "method")
    private String method;
    /**
     * 数据
     */
    @Field(value = "params")
    private Map<String, String[]> params;
    /**
     * 异常信息
     */
    @Field(value = "msg")
    private String msg;
    /**
     * 请求状态
     */
    @Field(value = "status")
    private String status;
}
