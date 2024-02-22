package com.example.securitydemo.controller;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @GetMapping("/List")
    @PreAuthorize("hasRole('ADMIN') and authentication.name == 'admin'")              //可以写一些表达式
    public List<User> getList(){
        return userService.list();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER_ADD')")
    public void add(@RequestBody User user){
        userService.saveUserDetails(user);
    }
}
