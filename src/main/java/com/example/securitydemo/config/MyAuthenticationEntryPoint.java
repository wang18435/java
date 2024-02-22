package com.example.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;

public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String localizedMessage = "需要登录";

        HashMap result = new HashMap();
        result.put("code", 0);
        result.put("message", localizedMessage);

        //将结果转化为json字符串
        String json = JSON.toJSONString(result);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}
