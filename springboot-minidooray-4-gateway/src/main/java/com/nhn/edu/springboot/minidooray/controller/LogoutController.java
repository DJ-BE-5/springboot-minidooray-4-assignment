package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout() {
        /**
         * todo(2)
         *  delete login information from Session-Redis
         *  and redirect at index page.
         */
        return "redirect:/";
    }
}
