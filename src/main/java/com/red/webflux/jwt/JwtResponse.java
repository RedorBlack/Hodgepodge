package com.red.webflux.jwt;

import java.io.Serializable;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 15:07 2019/8/19
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
