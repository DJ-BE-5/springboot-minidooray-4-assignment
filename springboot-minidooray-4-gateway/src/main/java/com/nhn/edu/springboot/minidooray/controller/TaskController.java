package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{projectId}/task")
public class TaskController {

    @GetMapping
    public String getProject(@PathVariable("projectId") Long projectId) {
        return "tasks";
    }
    @GetMapping("/{taskId}")
    public String getTask() {
        /**
         * todo(8)
         *
         */

        return "task";
    }
}
