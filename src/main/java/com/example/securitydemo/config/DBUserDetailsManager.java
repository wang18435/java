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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
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
        queryWrapper.eq("username", username);    //与数据库中保持一致
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
////            authorities.add(()->"USER_LIST");
//            authorities.add(()->"USER_ADD");
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    user.getEnabled(),
//                    true, //用户账号是否过期
//                    true,                 //用户凭证是否过期
//                    true,                 //用户是否未被锁定
//                    authorities);         //权限列表

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .disabled(!user.getEnabled())
                    .credentialsExpired(false)
                    .accountLocked(false)
//                    .roles("ADMIN")                  //roles和authorities不可以同时使用，会被覆盖掉
                    .authorities("USER_ADD","USER_UPDATE")
                    .build()
                    ;
        }
    }


    //向数据库中插入新的用户信息
    public void createUser(UserDetails userDetails) {
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        userMapper.insert(user);
    }

}
