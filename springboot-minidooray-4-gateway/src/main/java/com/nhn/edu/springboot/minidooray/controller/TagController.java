package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{projectId}/tag")
public class TagController {
    @GetMapping
    public String getTag() {
        /**
         * todo(6)
         *
         */

        return "tag";
    }
}
