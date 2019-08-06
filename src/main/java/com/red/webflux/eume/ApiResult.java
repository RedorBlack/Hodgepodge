package com.red.webflux.eume;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 18:19 2019/8/6
 */
public class ApiResult {

    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiResult(ApiEnum apiEnum, Object data) {
        this.code = apiEnum.getCode();
        this.msg = apiEnum.getMsg();
        this.data = data;

    }
}
