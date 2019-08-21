package com.red.webflux.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 14:34 2019/8/21
 */
public class TokenNotFunchException extends IOException {


    @Getter
    @Setter
    public String msg;

    public TokenNotFunchException(String message) {
        super(message);
        this.msg = msg;
    }
}
