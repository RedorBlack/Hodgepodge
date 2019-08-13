package com.red.webflux.mongo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 18:12 2019/8/13
 */
@Data
public class PageResult<T> {

    /**
     * 页码，从1开始
     */
    private Integer pageNum;

    /**
     * 页面大小
     */
    private Integer pageSize;


    /**
     * 总数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 数据
     */
    private List<T> list;

}
