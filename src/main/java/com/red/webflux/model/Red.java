package com.red.webflux.model;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:57 2019/8/1
 */
@Document(collection = "red_demo")
@Data
public class Red extends BaseEntity {



    @NotNull(message = "name 不能为空")
    @Field("red_name")
    private String name;

    @Field("message")
    private String message;

    private int age;


}
