package com.smile.security.basic;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @ClassName User
 * @Author smile
 * @date 2022.07.17 16:41
 */
@Getter
@Setter
public class User {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
