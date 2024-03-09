package com.nhn.edu.springboot.minidooray.controller;

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
    private final ApiProperties apiProperties;
    private final RestTemplate restTemplate;

    public LoginController(RedisTemplate redisTemplate, ApiProperties apiProperties, RestTemplate restTemplate) {
        this.redisTemplate = redisTemplate;
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
    }
    @PostMapping("/login")
    public String login() {
        /**
         * todo(1)
         *  post login information at Account-API
         *  and save at Session-Redis.
         */
        redisTemplate.opsForValue().set("foo", "bar");
        log.info("value at foo: " + redisTemplate.opsForValue().get("foo"));

        log.info("account API end point: " + apiProperties.getAccountEndPoint());
        log.info("task API end point: " + apiProperties.getTaskEndPoint());

        return "redirect:/project";
    }
}
