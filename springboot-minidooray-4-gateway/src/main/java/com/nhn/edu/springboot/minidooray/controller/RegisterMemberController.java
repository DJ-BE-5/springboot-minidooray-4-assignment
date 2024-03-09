package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{projectId}/member/add")
public class RegisterMemberController {
    @GetMapping
    public String getRegisterMember() {
        /**
         * todo(12)
         *
         */

        return "registerMember";
    }

}
