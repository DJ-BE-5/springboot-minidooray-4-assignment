package com.nhn.edu.springboot.minidooray.controller;

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
@RequestMapping("/project/{projectId}/tag")
public class TagController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public TagController(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public ModelAndView getTag(@PathVariable Long projectId) {
        /**
         * todo(6)
         *  getTag from task-api
         */
        ModelAndView mav = new ModelAndView("tag");
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            ResponseEntity<List<TaskDto>> taskExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/" + projectId + "/tag",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            return mav;
        } catch(Exception e) {
            mav.setViewName("error");
            return mav;
        }
    }

    @GetMapping("/{tagId}/delete")
    public String deleteTag(@PathVariable Long tagId,
                            @PathVariable Long projectId) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/tag/delete/" + tagId,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/tag";
        } catch(Exception e) {
            return "error";
        }
    }
}
