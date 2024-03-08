package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    @GetMapping
    public String getMyAccount() {
        /**
         * todo(9)
         *
         */

        return "account";
    }
}
