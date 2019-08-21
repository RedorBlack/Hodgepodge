package com.red.webflux.model;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
/**
 * @Author: Red
 * @Descpription:
 * @Date: 15:42 2019/8/21
 */
@Document(collection = "log")
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
    private String ip;

}
