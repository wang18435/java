package com.example.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity           //开启基于方法的授权
//@EnableWebSecurity      //开启SpringSecurity的自动配置（在SpringBoot项目中可以省略此注解）
public class WebSecurityConfig {

    //--------------------------------------------------------------------------------------------------
    //默认配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //开启授权保护
        http.authorizeRequests(
                authorize -> authorize
                        //只有User_List权限的用户可以访问/user/list
//                        .requestMatchers("/user/List").hasAuthority("USER_LIST")
//                        //具有USER_ADD权限的用户/user/add
//                        .requestMatchers("/user/add").hasAuthority("USER_ADD")
//                        .requestMatchers("/user/**").hasRole("ADMIN")
                        //对所有请求开启授权保护
                        .anyRequest()
                        //已认证的请求会被自动授权
                        .authenticated()
        );
//                .formLogin(withDefaults());//表单授权方式

        http.formLogin(form -> {
            form.loginPage("/login").permitAll()
                    //下面是用来自定义表单的用户名参数和密码参数，默认值是username和password
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureUrl("/login?failure")   //检查失败时跳转的地址，默认值是error
                    .successHandler(new MyAuthenticationSuccesHander())      //认证成功时的处理
                    .failureHandler(new MyAuthenticationFailureHander())
            ;
        });


        //注销成功时的处理
        http.logout(logout -> {
            logout.logoutSuccessHandler(new MyLogoutSuccessHandler());
        });
//                .httpBasic(withDefaults());//基本授权方式

        //未认证的处理
        http.exceptionHandling(except -> {
            except.authenticationEntryPoint(new MyAuthenticationEntryPoint())
                    .accessDeniedHandler(new MyAccessDeniedHandler());
        });

        http.sessionManagement(session -> {
            session.maximumSessions(1).expiredSessionStrategy(new MySessionInformationExpiredStrategy());
        });

        //跨域
        http.cors(withDefaults());

        //关闭CSRF防御
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