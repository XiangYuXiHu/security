package com.smile.security.config;

import com.smile.security.basic.MyPasswordEncoder;
import com.smile.security.basic.MyUserService;
import com.smile.security.filter.JWTAuthenticationFilter;
import com.smile.security.filter.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @Description
 * @ClassName WebSecurityConfig
 * @Author smile
 * @date 2022.07.17 14:42
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()   //关闭跨站请求防护
                //允许不登陆就可以访问的方法，多个用逗号分隔
                .authorizeRequests().antMatchers("/test", "/name").permitAll()
                //其他需要授权访问
                .anyRequest().authenticated()
                .and()
                //增加登录拦截
                .addFilter(new JWTLoginFilter(authenticationManager()))
                //增加是否登录过滤
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                // 前后端分离是无状态的，所以暫時不用session，將登陆信息保存在token中
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //覆盖UserDetailsService类
        auth.userDetailsService(myUserService)
                //覆盖默认的密码验证类
                .passwordEncoder(myPasswordEncoder);
    }
}
