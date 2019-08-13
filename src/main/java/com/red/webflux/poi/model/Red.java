package com.red.webflux.poi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 10:22 2019/8/12
 */
@Data
public class Red {

    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    private int age;
    @Excel(name = "时间")
    private Long time;
    @Excel(name = "地址")
    private String address;

}
