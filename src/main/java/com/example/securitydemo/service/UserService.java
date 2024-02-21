package com.example.securitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.securitydemo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    void saveUserDetails(User user);
}