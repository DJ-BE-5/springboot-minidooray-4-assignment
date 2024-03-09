package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @GetMapping
    public String getRegister() {
        return "register";
    }

    @PostMapping
    public String postRegister() {
        /**
         * todo(0)
         *  create account
         */
        return null;
    }
}
