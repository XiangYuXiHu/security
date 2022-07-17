package com.smile.security.basic;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 实现UserDetailsService接口，用户登录调用
 *
 * @Description
 * @ClassName MyUserService
 * @Author smile
 * @date 2022.07.17 14:49
 */
@Service
public class MyUserService implements UserDetailsService {
    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //判断username在数据库中是否存在
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setUsername(username);
        myUserDetails.setPassword("123456");
        return myUserDetails;
    }
}
