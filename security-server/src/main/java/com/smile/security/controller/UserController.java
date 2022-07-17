package com.smile.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName UserController
 * @Author smile
 * @date 2022.07.17 16:31
 */
@RestController
public class UserController {

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
