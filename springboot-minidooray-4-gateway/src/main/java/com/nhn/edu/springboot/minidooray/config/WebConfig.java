package com.nhn.edu.springboot.minidooray.config;

import com.nhn.edu.springboot.minidooray.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;

    public WebConfig(RedisTemplate redisTemplate, RestTemplate restTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }

    @Bean
    public LoginCheckInterceptor loginCheckInterceptor() {
        return new LoginCheckInterceptor(redisTemplate, restTemplate);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor())
                .addPathPatterns("/project**")
                .addPathPatterns("/student**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register");
    }
}
