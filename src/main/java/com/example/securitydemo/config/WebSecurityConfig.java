package com.example.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
//@EnableWebSecurity      //开启SpringSecurity的自动配置（在SpringBoot项目中可以省略此注解）
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        DBUserDetailsManager manager = new DBUserDetailsManager();
        return manager;
    }
}