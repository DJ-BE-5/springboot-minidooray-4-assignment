package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.MileStoneDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectAccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.dto.TaskDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/project/{projectId}/task")
public class TaskController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;
    private final RedisTemplate redisTemplate;

    public TaskController(RestTemplate restTemplate, ApiProperties apiProperties, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
        this.redisTemplate = redisTemplate;
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
            mav.addObject("projectId", projectId);

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

            String accountId = redisTemplate.opsForValue().get("user").toString();

            ResponseEntity<List<ProjectAccountDto>> projectAccountDtoExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/account/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            ProjectAccountDto projectAccountDto = projectAccountDtoExchange.getBody().stream()
                    .filter(p -> p.getProjectId().equals(projectId))
                    .findFirst()
                    .get();

            ResponseEntity<TaskDto> taskDtoExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/task/" + taskId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            mav.addObject("task", taskDtoExchange.getBody());
            mav.addObject("isAuth", projectAccountDto.getAuth() == ProjectAccountDto.Auth.ADMIN);
            mav.addObject("projectId", projectId);
            mav.addObject("accountId", accountId);
        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/delete/{taskId}")
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
                    apiProperties.getTaskEndPoint() + "/task/delete/" + taskId,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/task";

        } catch(Exception e) {
            return "error";
        }
    }

    @PostMapping("/{taskId}/content/modify")
    public String modifyTask(@PathVariable("taskId") Long taskId,
                             @PathVariable("projectId") Long projectId,
                             HttpServletRequest request) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            TaskDto taskDto = new TaskDto();
            taskDto.setContent(request.getParameter("content"));

            HttpEntity<TaskDto> taskDtoHttpEntity = new HttpEntity<>(taskDto, httpHeaders);

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/task/modify/" + taskId,
                    HttpMethod.PUT,
                    taskDtoHttpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/task/" + taskId;

        } catch(Exception e) {
            return "error";
        }
    }
}
