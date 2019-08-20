package com.red.webflux.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:02 2019/8/16
 */
@Entity
@Table(name = "red_demo")
@Data
public class JpaDemo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    private int age;
    private String msg;

}
