package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.service.AccountService;
import com.nhn.edu.springboot.minidooray.util.SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import static com.nhn.edu.springboot.minidooray.util.SaltUtil.getEncrypt;
import static com.nhn.edu.springboot.minidooray.util.SaltUtil.getSalt;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccount(){

        return ResponseEntity.ok(accountService.findAllAccount());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String accountId) {
        Optional<Account> optionalAccount = accountService.findAccountById(accountId);

        if(optionalAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setAccountId(optionalAccount.get().getAccountId());
        accountDto.setEmail(optionalAccount.get().getEmail());

        return ResponseEntity.ok(accountDto);
}
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity <Account> createAccount (@RequestBody AccountDto accountDto) {

        Account account = new Account();

        account.setAccountId(accountDto.getAccountId());
        account.setEmail(accountDto.getEmail());
        account.setSalt(getSalt());
        account.setSaltedPassword(getEncrypt(accountDto.getPassword(), account.getSalt()));

        accountService.createAccount(account);

        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity <Void> deleteAccount (@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.notFound().build();
    }
}
