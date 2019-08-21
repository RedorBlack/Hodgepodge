package com.red.webflux.exception;


import com.red.webflux.eume.ApiEnum;
import com.red.webflux.eume.ResultBean;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;


/**
 * @Author: Red
 * @Descpription: 全局数据验证失败
 * @Date: 10:58 2019/7/29
 */
@RestControllerAdvice
public class BizExceptionHandler {

    /**
     * 用来处理bean validation异常
     * * @param ex
     *
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultBean resolveConstraintViolationException(ConstraintViolationException ex) {
        ResultBean resultBean = null;
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            resultBean = ResultBean.error(ApiEnum.ERROR.getCode(), errorMessage);
        }
        return resultBean;
    }

    /**
     * 方法级别的验证
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResultBean resultBean = null;
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (CollectionUtils.isNotEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            resultBean = ResultBean.error(ApiEnum.ERROR.getCode(), errorMessage);
        }
        return resultBean;
    }


    /**
     * jackson
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultBean illegalArgumentException(IllegalArgumentException ex) {
        return ResultBean.error(ApiEnum.ERROR.getCode(), ex.getMessage());

    }

    /**
     * jwt token失效
     *
     * @param EX
     * @return
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResultBean ExpiredJwtException(ExpiredJwtException EX) {
        return ResultBean.error(ApiEnum.ERROR.getCode(), EX.getMessage());

    }

    /**
     * 用户名未找到
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResultBean UsernameNotFoundException(UsernameNotFoundException ex) {
        return ResultBean.error(ApiEnum.ERROR.getCode(), ex.getMessage());
    }

    /**
     * token 格式不正确
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResultBean MalformedJwtException(MalformedJwtException ex) {
        return ResultBean.error(ApiEnum.ERROR.getCode(), ex.getMessage());
    }

    @ExceptionHandler(TokenNotFunchException.class)
    public ResultBean TokenNotFunchException(TokenNotFunchException ex) {
        return ResultBean.error(ApiEnum.ERROR.getCode(), ex.getMsg());
    }

}
