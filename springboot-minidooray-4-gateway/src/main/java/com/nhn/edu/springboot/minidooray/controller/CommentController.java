package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.dto.CommentDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class CommentController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public CommentController(RedisTemplate redisTemplate, RestTemplate restTemplate, ApiProperties apiProperties) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }
    @PostMapping("/project/{projectId}/task/{taskId}/comment/create")
    public String createTask(@PathVariable Long projectId,
                             @PathVariable Long taskId,
                             HttpServletRequest request) {
        /**
         * todo(15)
         *  post comment at project
         */
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            String myAccountId = redisTemplate.opsForValue().get("user").toString();
            ProjectDto projectDto = new ProjectDto();

            ResponseEntity<AccountDto> accountDtoExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/account/" + myAccountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            CommentDto commentDto = new CommentDto();

            commentDto.setAccountId(accountDtoExchange.getBody().getAccountId());
            commentDto.setRegisteredTime(LocalDateTime.now());
            commentDto.setTaskId(taskId);

            HttpEntity<CommentDto> commentDtoHttpEntity = new HttpEntity<>(commentDto, httpHeaders);

            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/comment/create",
                    HttpMethod.POST,
                    commentDtoHttpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/task/" + taskId;
        } catch(Exception e) {
            return "error";
        }
    }
}
