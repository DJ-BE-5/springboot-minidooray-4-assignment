package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @GetMapping
    public String getProjectList() {
        /**
         * todo(3)
         *  get login information from Session-Redis
         *  and get project list from Task-API.
         */

        return "projectList";
    }
}
