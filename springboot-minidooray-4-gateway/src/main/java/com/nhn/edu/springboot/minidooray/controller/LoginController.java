package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectAccountDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class LoginController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public LoginController(RedisTemplate redisTemplate, RestTemplate restTemplate, ApiProperties apiProperties) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }
    @PostMapping("/login")
    public String login(AccountDto accountDto) {
        /**
         * todo(1)
         *  post login information at Account-API
         *  and save at Session-Redis.
         */
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<AccountDto> accountDtoHttpEntity = new HttpEntity<>(accountDto, httpHeaders);

            // todo: change api url
            ResponseEntity exchange = restTemplate.exchange(
                    "http://localhost:9090" + "/login",
                    HttpMethod.POST,
                    accountDtoHttpEntity,
                    new ParameterizedTypeReference<>() {}
            );
            System.out.println(exchange.getHeaders());

            if(exchange.getStatusCode().value() == 200) {
                redisTemplate.opsForValue().set("user", accountDto.getAccountId());
            }

        } catch (Exception e) {
            return "error";
        }

        return "redirect:/project";
    }
}
