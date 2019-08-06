package com.red.webflux.eume;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 18:20 2019/8/6
 */
public enum ApiEnum {

    SUCCESS(200, "成功"),
    ERROR(-999, "失败");

    private int code;
    private String msg;

    ApiEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
