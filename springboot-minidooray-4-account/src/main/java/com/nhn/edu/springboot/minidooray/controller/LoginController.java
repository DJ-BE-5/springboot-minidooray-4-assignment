package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static com.nhn.edu.springboot.minidooray.util.SaltUtil.getEncrypt;

@RestController
public class LoginController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountDto accountDto) {
        if(!accountRepository.existsById(accountDto.getAccountId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", "FAILED"));
        }

        Account account = accountRepository.findById(accountDto.getAccountId()).get();
        String saltedPassword = getEncrypt(accountDto.getPassword(), account.getSalt());

        if (account != null && saltedPassword.equals(account.getSaltedPassword())){
            return ResponseEntity.ok().body(Map.of("result", "OK"));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", "FAILED"));
        }
    }
}


