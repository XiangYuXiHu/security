package com.smile.security.basic;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 实现UserDetails接口，只留必需的属性，也可添加自己需要的属性
 * 这个类是用来存储登录成功后的用户数据，登录成功后,使用下列代码获取：
 * MyUserDetails myUserDetails= (MyUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
 *
 * @Description
 * @ClassName MyUserDetails
 * @Author smile
 * @date 2022.07.17 14:56
 */
public class MyUserDetails implements UserDetails {

    /**
     * 登陆用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
