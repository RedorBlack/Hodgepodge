package com.red.webflux.eume;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 18:19 2019/8/6
 */
@Data
public class ResultBean {

    private int code;
    private String msg;
    private T data;


    /**
     * success
     * @return
     */
    public static ResultBean success() {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMsg("success");
        return resultBean;
    }

    /**
     * error
     * @param code
     * @param msg
     * @return
     */
    public static ResultBean error(int  code,String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMsg(msg);
        return resultBean;
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultBean success(T data) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMsg("success");
        resultBean.setData(data);
         return null;
    }
}
