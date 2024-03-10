package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectAccountDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/project/{projectId}/member/add")
public class RegisterMemberController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public RegisterMemberController(RedisTemplate redisTemplate, RestTemplate restTemplate, ApiProperties apiProperties) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public String RegisterMember(@PathVariable("projectId") Long projectId, HttpServletRequest request) {
        /**
         * todo(12)
         *  add relation between member and project
         */

        String accountId = request.getParameter("memberId");

        try {
            String myAccountId = redisTemplate.opsForValue().get("user").toString();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            ResponseEntity<AccountDto> accountDtoExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/account/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // Auth check
            ResponseEntity<ProjectAccountDto> projectAccountDtoEntity = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/project/" + projectId + "/account/" + myAccountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            if(projectAccountDtoEntity.getBody().getAuth() != ProjectAccountDto.Auth.ADMIN) {
                return "error";
            }

            ProjectAccountDto projectAccountDto = new ProjectAccountDto();

            projectAccountDto.setProjectId(projectId);
            projectAccountDto.setAccount(accountDtoExchange.getBody());
            projectAccountDto.setAuth(ProjectAccountDto.Auth.USER);

            HttpEntity<ProjectAccountDto> projectAccountDtoHttpEntity = new HttpEntity<>(projectAccountDto, httpHeaders);

            restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/project/" + projectId + "/account/" + accountId,
                    HttpMethod.POST,
                    projectAccountDtoHttpEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/member";
        } catch (Exception e) {
            return "error";
        }

    }

}
