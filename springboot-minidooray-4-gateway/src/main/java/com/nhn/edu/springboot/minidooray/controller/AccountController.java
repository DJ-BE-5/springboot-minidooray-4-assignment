package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public AccountController(RedisTemplate redisTemplate, RestTemplate restTemplate, ApiProperties apiProperties) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }
    /**
     * todo(9)
     *  make account modify post-api
     *  make account modify page get-api
     */

    @GetMapping
    public String getMyAccount() {
        return "account";
    }

    @PostMapping
    public ModelAndView updateAccount(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/project");

        try {
            String accountId = redisTemplate.opsForValue().get("user").toString();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            ResponseEntity<AccountDto> accountDtoExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/account/" + accountId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            AccountDto accountDto = accountDtoExchange.getBody();
            HttpEntity<AccountDto> accountDtoEntity = new HttpEntity<>(accountDto, httpHeaders);

            // todo: change api url
            ResponseEntity<ProjectDto> projectExchange = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/account/" + accountId,
                    HttpMethod.PUT,
                    accountDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }
}
