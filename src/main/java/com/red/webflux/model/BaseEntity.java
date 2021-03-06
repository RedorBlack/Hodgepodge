package com.red.webflux.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:02 2019/8/1
 */
@Setter
@Getter
public abstract class BaseEntity implements Serializable {


    @Id
    @Field("_id")
    private String id;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private Date updatedDate;

    @Version
    private Long version;

    private Boolean delete = Boolean.FALSE;


}
