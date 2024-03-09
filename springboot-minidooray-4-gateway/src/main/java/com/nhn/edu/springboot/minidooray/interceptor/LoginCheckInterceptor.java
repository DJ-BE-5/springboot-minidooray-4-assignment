package com.nhn.edu.springboot.minidooray.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final RedisTemplate redisTemplate;
    private final RestTemplate restTemplate;

    public LoginCheckInterceptor(RedisTemplate redisTemplate, RestTemplate restTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /**
         * todo(13) implement login check function and delete log
         */
        if(!redisTemplate.hasKey("user")) {
            response.sendRedirect("/");
            return false;
        }

//        Account account = (Account) redisTemplate.opsForValue().get("user").toString();


//        if (account == null || !account.login(user[0], user[1])) {
//            response.sendRedirect("/");
//            return false;
//        }
        log.info(redisTemplate.opsForValue().get("user").toString());

        return true;
    }
}
