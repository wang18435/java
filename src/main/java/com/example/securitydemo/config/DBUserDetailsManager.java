package com.example.securitydemo.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class DBUserDetailsManager implements UserDetailsService, UserDetailsPasswordService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    //从数据库中获取用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username",username);    //与数据库中保持一致
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }else{
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEnabled(),
                    true, //用户账号是否过期
                    true,                 //用户凭证是否过期
                    true,                 //用户是否未被锁定
                    authorities);         //权限列表
        }
    }

}
