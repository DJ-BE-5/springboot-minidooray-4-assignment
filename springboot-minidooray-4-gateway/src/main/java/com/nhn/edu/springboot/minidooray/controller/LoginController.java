package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
public class LoginController {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;

    public LoginController(RedisTemplate redisTemplate, RestTemplate restTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }
    @PostMapping("/login")
    public String login(AccountDto accountDto) {
        /**
         * todo(1)
         *  post login information at Account-API
         *  and save at Session-Redis.
         */
        redisTemplate.opsForValue().set("user", accountDto.getAccountId());

        return "redirect:/project";
    }
}
