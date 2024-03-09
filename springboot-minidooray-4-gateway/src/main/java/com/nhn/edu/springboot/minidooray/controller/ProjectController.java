package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectAccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public ProjectController(RedisTemplate redisTemplate, RestTemplate restTemplate, ApiProperties apiProperties) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public ModelAndView getProjectList() {
        ModelAndView mav = new ModelAndView("projectList");
        /**
         * todo(3)
         *  get login information from Session-Redis
         *  and get project list from Task-API.
         */
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            String accountId = redisTemplate.opsForValue().get("user").toString();

            // todo: change api url
            ResponseEntity<List<ProjectDto>> projectExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            List<ProjectDto> projectDtoList = projectExchange.getBody();

            for(ProjectDto projectDto : projectDtoList) {
                // todo: change api url
                ResponseEntity<List<ProjectAccountDto>> projectAccountExchange = restTemplate.exchange(
                        apiProperties.getAccountEndPoint() + "/projectaccount/" + projectDto.getProjectId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                List<AccountDto> accountDtoList = projectAccountExchange.getBody().stream()
                        .map(projectAccountDto -> projectAccountDto.getAccount())
                        .collect(Collectors.toList());

                projectDto.setAccounts(accountDtoList);
            }

            mav.addObject("projectDtoList", projectDtoList);

            return mav;
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
