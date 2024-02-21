package com.example.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//@EnableWebSecurity      //开启SpringSecurity的自动配置（在SpringBoot项目中可以省略此注解）
public class WebSecurityConfig {

    //--------------------------------------------------------------------------------------------------
    //默认配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //开启授权保护
        http.authorizeRequests(
                authorize -> authorize
                        //对所有请求开启授权保护
                        .anyRequest()
                        //已认证的请求会被自动授权
                        .authenticated()
                )
//                .formLogin(withDefaults());//表单授权方式
        .formLogin(form -> {
           form.loginPage("/login").permitAll()
                   //下面是用来自定义表单的用户名参数
                   .usernameParameter("username")
                   //配置自定义表单的密码参数
                   .passwordParameter("password");
        });
//                .httpBasic(withDefaults());//基本授权方式

        http.csrf(csrf -> csrf.disable());
        return http.build();

    //--------------------------------------------------------------------------------------------------
    //只需要UserDetailsService一个对象，可以在DBUserDetailsManager中加注解是一样的
//    @Bean
//    public UserDetailsService userDetailsService() {
//        //创建基于数据库的用户信息管理器
//        DBUserDetailsManager manager = new DBUserDetailsManager();
//        return manager;
//
    }
}