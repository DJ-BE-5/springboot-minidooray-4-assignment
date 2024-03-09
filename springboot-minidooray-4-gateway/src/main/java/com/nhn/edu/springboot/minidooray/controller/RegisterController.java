package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public RegisterController(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public String getRegister() {
        return "register";
    }

    @PostMapping
    public ModelAndView postRegister(@RequestBody AccountDto accountDto) {
        /**
         * todo(0)
         *  create account
         */
        ModelAndView mav = new ModelAndView("redirect:/");
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<AccountDto> accountDtoEntity = new HttpEntity<>(accountDto, httpHeaders);

            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/account",
                    HttpMethod.POST,
                    accountDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return mav;
        } catch(Exception e) {
            mav.setViewName("error");
            return mav;
        }
    }
}
