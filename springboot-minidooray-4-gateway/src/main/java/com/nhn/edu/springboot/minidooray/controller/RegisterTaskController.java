package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.MileStoneDto;
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
@RequestMapping("/project/{projectId}/task/create")
public class RegisterTaskController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public RegisterTaskController(RedisTemplate redisTemplate, RestTemplate restTemplate, ApiProperties apiProperties) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public String getRegisterTask(@PathVariable("projectId") Long projectId) {
        return "registerTask";
    }

    @PostMapping
    public String postTask(HttpServletRequest request,
                           @PathVariable Long projectId) {
        /**
         * todo(4)
         *  implements posting task on task-api
         */
        ModelAndView mav = new ModelAndView("redirect:/project/" + projectId + "/task");

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            TaskDto taskDto = new TaskDto();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            LocalDateTime startDateInput = LocalDateTime.parse(request.getParameter("startDate"));
            LocalDateTime endDateInput = LocalDateTime.parse(request.getParameter("endDate"));

            MileStoneDto mileStoneDto = new MileStoneDto();
            mileStoneDto.setStartDate(startDateInput);
            mileStoneDto.setEndDate(endDateInput);

            taskDto.setTitle(title);
            taskDto.setContent(content);
            taskDto.setMileStone(mileStoneDto);

            HttpEntity<TaskDto> taskDtoEntity = new HttpEntity<>(taskDto, httpHeaders);

            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/task/create/project/" + projectId,
                    HttpMethod.POST,
                    taskDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/task";
        } catch(Exception e) {
            return "registerTask";
        }
    }
}
