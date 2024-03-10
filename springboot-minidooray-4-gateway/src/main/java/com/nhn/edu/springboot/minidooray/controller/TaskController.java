package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.dto.TaskDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/project/{projectId}/task")
public class TaskController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public TaskController(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public ModelAndView getTasks(@PathVariable("projectId") Long projectId) {
        /**
         * todo(8)
         *  get project's task
         */

        ModelAndView mav = new ModelAndView("tasks");

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

            mav.addObject("tasks", projectExchange.getBody().getTasks());

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @GetMapping("/{taskId}")
    public ModelAndView getTask(@PathVariable("projectId") Long projectId,
                          @PathVariable("taskId") Long taskId) {
        /**
         * todo(8-1)
         *  get task by taskId
         */
        ModelAndView mav = new ModelAndView("task");

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            ResponseEntity<TaskDto> taskDtoExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/task/" + taskId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            mav.addObject("task", taskDtoExchange.getBody());

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("projectId") Long projectId,
                             @PathVariable("taskId") Long taskId) {
        /**
         * todo(8-2)
         *  delete task by taskId
         */

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/task/" + taskId,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/task";

        } catch(Exception e) {
            return "error";
        }
    }
}
