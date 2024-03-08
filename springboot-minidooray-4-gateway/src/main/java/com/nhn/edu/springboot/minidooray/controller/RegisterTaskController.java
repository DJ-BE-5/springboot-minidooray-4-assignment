package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{projectId}/task/create")
public class RegisterTaskController {

    @GetMapping
    public String getRegisterTask(@PathVariable("projectId") Long projectId) {
        return "registerTask";
    }

//    @GetMapping("/{taskId}")
//    public String getTask() {
//        /**
//         * todo(8)
//         *
//         */
//
//        return "task";
//    }
}
