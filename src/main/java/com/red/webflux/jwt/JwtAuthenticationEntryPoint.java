package com.red.webflux.jwt;

import com.alibaba.fastjson.JSONObject;
import com.red.webflux.eume.ResultBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Red
 * @Descpription: 资源拦截 保护 未通过验证直接拒绝
 * @Date: 15:11 2019/8/19
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        //是否有异常栈
        Boolean flag = Objects.isNull(httpServletRequest.getAttribute("error"));
        if (!flag) {
            response.getWriter().write(JSONObject.toJSONString(ResultBean.error(HttpServletResponse.SC_UNAUTHORIZED, (String) httpServletRequest.getAttribute("error"))));
            return;
        }
        response.getWriter().write(JSONObject.toJSONString(ResultBean.error(HttpServletResponse.SC_UNAUTHORIZED, "未授权")));
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
