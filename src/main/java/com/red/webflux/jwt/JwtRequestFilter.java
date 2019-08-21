package com.red.webflux.jwt;

import com.alibaba.fastjson.JSONObject;
import com.red.webflux.eume.ApiEnum;
import com.red.webflux.eume.ResultBean;
import com.red.webflux.exception.TokenNotFunchException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Red
 * @Descpription: 拦截请求
 * @Date: 15:08 2019/8/19
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                    if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } else {
                throw new TokenNotFunchException("JWT Token does not begin with Bearer String");
            }
        } catch (Exception ex) {
            StringBuilder builder = new StringBuilder();
            builder.append(ex.getMessage());
            request.setAttribute("error", builder.toString());
        } finally {
            chain.doFilter(request, response);
        }

    }
}

