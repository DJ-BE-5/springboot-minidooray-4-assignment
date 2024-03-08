package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{projectId}/member")
public class MemberController {
    @GetMapping
    public String getMemberList() {
        /**
         * todo(5)
         *
         */

        return "member";
    }

}
