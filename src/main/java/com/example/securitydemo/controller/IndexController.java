package com.example.securitydemo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {
    @GetMapping("/")
    public Map<Object, Object> index(){

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        Object credentials = authentication.getCredentials();//脱敏处理
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String name = authentication.getName();

        HashMap<Object, Object> result = new HashMap<>();
        result.put("username",name);
        result.put("authorities",authorities);

        return result;
    }
}
