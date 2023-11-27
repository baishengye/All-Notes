package com.cao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: huahua
 * @Date: 2020-11-25 16:44
 */

@EnableWebSecurity
public class SecurityConfig1 extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 授权的
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问，功能页有相应权限才能访问
        //链式编程
        //请求授权的规则
        http.authorizeRequests()
                .antMatchers("/","/toLogin").permitAll()
                .antMatchers("/level1/**").hasAuthority("1")
                .antMatchers("/level2/**").hasAuthority("2")
                .antMatchers("/level3/**").hasAuthority("3");

        //没有权限，默认到登录页面
        http.formLogin()//自定义编写登录页面
                .loginPage("/login")//登录页面设置
                .loginProcessingUrl("/login")//登录访问路径
                .defaultSuccessUrl("/index")//登陆成功后的路径
                .usernameParameter("ugh")
                .passwordParameter("upwd");


        //防止网站攻击
        http.csrf().disable();//登出可能存在失败的原因
        //注销功能
        http.logout().logoutSuccessUrl("/toLogin");
        //开启记住我功能
        http.rememberMe().rememberMeParameter("remember");

    }

    /**
     * 认证的
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }
}
