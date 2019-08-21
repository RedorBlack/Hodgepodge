package com.red.webflux.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 17:37 2019/8/19
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Map map = new HashMap(16);
        map.put("code", "403");
        map.put("msg", e.getMessage());
        map.put("data","");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
