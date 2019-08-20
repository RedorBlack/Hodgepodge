package com.red.webflux.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 15:06 2019/8/19
 */
@Data
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
}
