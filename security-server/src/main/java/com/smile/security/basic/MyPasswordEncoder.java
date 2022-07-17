package com.smile.security.basic;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义的密码加密方法，实现PasswordEncoder接口
 * 类主要是对密码加密的处理以及用户传递过来的密码和数据库密码（UserDetailsService中的密码）进行比对
 *
 * @Description
 * @ClassName MyPasswordEncoder
 * @Author smile
 * @date 2022.07.17 14:43
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
    /**
     * 加密方法可根据需要进行修改
     */
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equalsIgnoreCase(s);
    }
}
