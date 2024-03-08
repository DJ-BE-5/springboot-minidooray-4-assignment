package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @PostMapping("/login")
    public String login() {
        /**
         * todo(1)
         *  post login information at Account-API
         *  and save at Session-Redis.
         */
        return "redirect:/project";
    }
}
