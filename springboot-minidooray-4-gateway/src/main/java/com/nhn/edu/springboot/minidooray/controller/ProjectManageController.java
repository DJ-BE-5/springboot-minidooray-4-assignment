package com.nhn.edu.springboot.minidooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project/{projectId}/manage")
public class ProjectManageController {
    @GetMapping
    public ModelAndView getProjectManage() {
        /**
         * todo(7)
         *  this page will show delete and modify name button.
         */

        ModelAndView mav = new ModelAndView("projectManage");
        mav.addObject("projectName", "미니 두레이 만들기 프로젝트");

        return mav;
    }

    @PostMapping("/modify")
    public ModelAndView modifyProjectName() {
        /**
         * todo(10)
         *  make function which can modify project name.
         */
        return null;
    }

    @PostMapping("/delete")
    public ModelAndView deleteProject() {
        /**
         * todo(11)
         *  make function which can delete project and other entities associated with Project.
         */
        return null;
    }
}
