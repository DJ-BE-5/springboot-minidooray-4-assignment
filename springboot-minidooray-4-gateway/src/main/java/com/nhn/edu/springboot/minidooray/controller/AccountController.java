package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {
    /**
     * todo(9)
     *  make account modify post-api
     *  make account modify page get-api
     */

    @GetMapping
    public ModelAndView getMyAccount() {
        ModelAndView mav = new ModelAndView("account");

        return mav;
    }

    public ModelAndView updateAccount(AccountDto accountDto) {
        ModelAndView mav = new ModelAndView("redirect:/account");



        return mav;
    }
}
