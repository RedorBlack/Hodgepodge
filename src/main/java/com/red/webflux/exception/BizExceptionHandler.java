package com.red.webflux.exception;


import com.red.webflux.eume.ApiEnum;
import com.red.webflux.eume.ApiResult;
import org.apache.commons.collections4.CollectionUtils;
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
    public ApiResult resolveConstraintViolationException(ConstraintViolationException ex) {
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
            return new ApiResult(ApiEnum.ERROR, errorMessage);
        }
        return new ApiResult(ApiEnum.ERROR, ex.getMessage());

    }

    /**
     * 方法级别的验证
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResult resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
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
            return new ApiResult(ApiEnum.ERROR, errorMessage);
        }
        return new ApiResult(ApiEnum.ERROR, ex.getMessage());
    }


}
