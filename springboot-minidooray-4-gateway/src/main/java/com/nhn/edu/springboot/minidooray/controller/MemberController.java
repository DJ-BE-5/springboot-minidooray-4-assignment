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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project/{projectId}/member")
public class MemberController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public MemberController(RestTemplate restTemplate, ApiProperties apiProperties) {
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
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            ResponseEntity<List<ProjectAccountDto>> projectAccountExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/projectaccount/" + projectId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            List<AccountDto> accountDtoList = projectAccountExchange.getBody().stream()
                    .map(projectAccountDto -> projectAccountDto.getAccount())
                    .collect(Collectors.toList());

            mav.addObject("accountDtoList", accountDtoList);
        } catch (Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @GetMapping("/{memberId}/delete")
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
                    apiProperties.getAccountEndPoint() + "/account/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            projectAccountDto.setAccount(accountDto.getBody());
            projectAccountDto.setProjectId(projectId);

            HttpEntity<ProjectAccountDto> projectAccountDtoEntity = new HttpEntity<>(projectAccountDto, httpHeaders);

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/projectaccount",
                    HttpMethod.DELETE,
                    projectAccountDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return "redirect:/project/" + projectId + "/member";
        } catch(Exception e) {
            return "error";
        }
    }


}
