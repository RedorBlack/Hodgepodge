package com.red.webflux.model;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:57 2019/8/1
 */
@Document(collection = "red_demo")
@Data
public class Red extends BaseEntity {

    @Field("red_name")
    private String name;
    @Field("message")
    private String message;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
