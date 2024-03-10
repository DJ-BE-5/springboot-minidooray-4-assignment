package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectAccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
            ResponseEntity<List<ProjectAccountDto>> projectAccountDtoExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/account/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            List<ProjectAccountDto> projectAccountDtoList = projectAccountDtoExchange.getBody().stream()
                    .filter(projectAccountDto -> projectAccountDto.getAccountId().equals(accountId))
                    .collect(Collectors.toList());

            List<ProjectDto> projectDtoList = new ArrayList<>();

            for(ProjectAccountDto projectDto : projectAccountDtoList) {
                // todo: change api url
                ResponseEntity<ProjectDto> projectDtoExchange = restTemplate.exchange(
                        apiProperties.getTaskEndPoint() + "/project/" + projectDto.getProjectId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                projectDtoList.add(projectDtoExchange.getBody());
            }

            mav.addObject("projectDtoList", projectDtoList);


            return mav;
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/create")
    public String createProject(HttpServletRequest request) {
        String projectTitle = request.getParameter("projectTitle");

        if(projectTitle == null || projectTitle.equals("")) {
            return "error";
        }

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            String myAccountId = redisTemplate.opsForValue().get("user").toString();
            ProjectDto projectDto = new ProjectDto();

            ResponseEntity<AccountDto> accountDtoExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/" + myAccountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            projectDto.setTitle(projectTitle);
            projectDto.setStatus(ProjectDto.Status.ACTIVATED);

            HttpEntity<ProjectDto> projectDtoHttpEntity = new HttpEntity<>(projectDto, httpHeaders);

            ResponseEntity<ProjectDto> projectDtoExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/create",
                    HttpMethod.POST,
                    projectDtoHttpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            ProjectAccountDto projectAccountDto = new ProjectAccountDto();

            projectAccountDto.setProjectId(projectDtoExchange.getBody().getProjectId());
            projectAccountDto.setAccount(accountDtoExchange.getBody());
            projectAccountDto.setAuth(ProjectAccountDto.Auth.ADMIN);

            HttpEntity<ProjectAccountDto> projectAccountDtoHttpEntity = new HttpEntity<>(projectAccountDto, httpHeaders);
            System.out.println(apiProperties.getAccountEndPoint() + "/project/" + projectDto.getProjectId() + "/account/" + accountDtoExchange.getBody().getAccountId() + "/save");
            restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/project/" + projectAccountDto.getProjectId() + "/account/" + accountDtoExchange.getBody().getAccountId() + "/save",
                    HttpMethod.POST,
                    projectAccountDtoHttpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project";
        } catch (Exception e) {
            return "error";
        }
    }
}
