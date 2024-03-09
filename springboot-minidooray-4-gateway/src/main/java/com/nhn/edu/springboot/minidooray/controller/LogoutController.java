package com.nhn.edu.springboot.minidooray.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class LogoutController {
    private final RedisTemplate redisTemplate;

    public LogoutController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @GetMapping("/logout")
    public String logout() {
        /**
         * todo(2)
         *  delete login information from Session-Redis
         *  and redirect at index page.
         */
        redisTemplate.delete("user");
        return "redirect:/";
    }
}
