package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/project/{projectId}/manage")
public class ProjectManageController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public ProjectManageController(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public ModelAndView getProjectManage(@PathVariable long projectId) {
        /**
         * todo(7)
         *  this page will show delete and modify name button.
         */
        ModelAndView mav = new ModelAndView("projectManage");

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            ResponseEntity<ProjectDto> projectExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/" + projectId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            mav.addObject("projectName", projectExchange.getBody().getTitle());

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/modify")
    public ModelAndView modifyProjectName(@PathVariable Long projectId,
                                          @RequestBody ProjectDto projectDto) {
        /**
         * todo(10)
         *  make function which can modify project name.
         */
        ModelAndView mav = new ModelAndView("/project/" + projectId + "/manage");

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<ProjectDto> projectDtoEntity = new HttpEntity<>(projectDto, httpHeaders);

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/" + projectId,
                    HttpMethod.PUT,
                    projectDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/delete")
    public ModelAndView deleteProject(@PathVariable Long projectId) {
        /**
         * todo(11)
         *  make function which can delete project and other entities associated with Project.
         */

        ModelAndView mav = new ModelAndView("redirect:/project");

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/" + projectId,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }
}
