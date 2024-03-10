package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.dto.TagDto;
import com.nhn.edu.springboot.minidooray.dto.TaskDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/project/{projectId}")
public class TagController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public TagController(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping("/task/{taskId}/tag/delete/{tagId}")
    public String deleteTag(@PathVariable Long tagId,
                            @PathVariable Long taskId,
                            @PathVariable Long projectId) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/task/" + taskId + "/tag/delete/" + tagId,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/task/" + taskId;
        } catch(Exception e) {
            return "error";
        }
    }

    @PostMapping("/task/{taskId}/tag/add")
    public String createTag(@PathVariable("taskId") Long taskId,
                            @PathVariable("projectId") Long projectId,
                            HttpServletRequest request) {
        /**
         * todo(14)
         *  create tag api
         */
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));


            ModelAndView mav = new ModelAndView("task");

            // todo: change api url
            ResponseEntity<TaskDto> taskDtoExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/task/" + taskId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            TagDto tagDto = new TagDto();
            tagDto.setTitle(request.getParameter("tagTitle"));

            List<TagDto> tags = taskDtoExchange.getBody().getTags();
            tags.add(tagDto);

            HttpEntity<TagDto> tagDtoHttpEntity = new HttpEntity<>(tagDto, httpHeaders);

            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/tag/create/" + taskId,
                    HttpMethod.POST,
                    tagDtoHttpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/task/" + taskId;
        } catch(Exception e) {
            return "error";
        }
    }
}
