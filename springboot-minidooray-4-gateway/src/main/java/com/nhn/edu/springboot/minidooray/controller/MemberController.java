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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project/{projectId}/member")
public class MemberController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public MemberController(RedisTemplate redisTemplate, RestTemplate restTemplate, ApiProperties apiProperties) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public ModelAndView getMemberList(@PathVariable Long projectId) {
        /**
         * todo(5)
         *  get member list from account-api
         */
        ModelAndView mav = new ModelAndView("member");

        try {
            String myAccountId = redisTemplate.opsForValue().get("user").toString();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            ResponseEntity<List<ProjectAccountDto>> projectAccountExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/project/" + projectId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            List<AccountDto> accountDtoList = projectAccountExchange.getBody().stream()
                    .map(projectAccountDto -> projectAccountDto.getAccount())
                    .collect(Collectors.toList());

            Map<String, ProjectAccountDto.Auth> authHashMap = new HashMap<>();

            for(int i = 0; i < accountDtoList.size(); i++) {
                authHashMap.put(accountDtoList.get(i).getAccountId(), projectAccountExchange.getBody().get(i).getAuth());
            }

            boolean isAuth = projectAccountExchange.getBody().stream()
                    .filter(projectAccountDto -> projectAccountDto.getAccountId().equals(myAccountId))
                    .findFirst().get().getAuth().equals(ProjectAccountDto.Auth.ADMIN);

            mav.addObject("accountDtoList", accountDtoList);
            mav.addObject("authHashMap", authHashMap);
            mav.addObject("projectId", projectId);
            mav.addObject("isAuth", isAuth);
        } catch (Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @GetMapping("/{accountId}/delete")
    public String deleteMember(@PathVariable Long projectId,
                                     @PathVariable String accountId) {
        /**
         * todo(5-1)
         *  get member list from account-api
         */
        ProjectAccountDto projectAccountDto = new ProjectAccountDto();

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            ResponseEntity<AccountDto> accountDto = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            projectAccountDto.setAccount(accountDto.getBody());
            projectAccountDto.setProjectId(projectId);

            HttpEntity<ProjectAccountDto> projectAccountDtoEntity = new HttpEntity<>(projectAccountDto, httpHeaders);

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/project/" + projectId + "/account/" + accountId,
                    HttpMethod.DELETE,
                    projectAccountDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/member";
        } catch(Exception e) {
            return "error";
        }
    }

    @PostMapping("/add")
    public String createProjectAccount(@PathVariable("projectId") Long projectId,
                                       HttpServletRequest request) {
        try {
            String accountId = request.getParameter("accountId");
            ProjectAccountDto projectAccountDto = new ProjectAccountDto();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            ResponseEntity<AccountDto> accountDto = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            projectAccountDto.setAuth(ProjectAccountDto.Auth.USER);

            HttpEntity<ProjectAccountDto> projectAccountDtoEntity = new HttpEntity<>(projectAccountDto, httpHeaders);

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/project/" + projectId + "/account/" + accountId + "/save",
                    HttpMethod.POST,
                    projectAccountDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/member";
        } catch(Exception e) {
            return "redirect:/project/" + projectId + "/member";
        }
    }

}
