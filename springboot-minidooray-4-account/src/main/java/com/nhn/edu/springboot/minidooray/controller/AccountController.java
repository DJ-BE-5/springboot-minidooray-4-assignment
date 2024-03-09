package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("/account")
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
    public ResponseEntity<Account> getAccountById(@PathVariable String accountId) {
        return accountService.findAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
}
    @PostMapping("/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity <Account> createAccount (@RequestBody AccountDto accountDto) {

        Account account = new Account();

        account.setAccountId(accountDto.getAccountId());
        account.setEmail(accountDto.getEmail());
        account.setSalt(getSalt());
        account.setSaltedPassword(getEncrypt(accountDto.getPassword(), account.getSalt()));

        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity <Void> deleteAccount (@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.notFound().build();
    }

    private String getSalt() {

        SecureRandom sr = new SecureRandom();
        byte [] salt = new byte[20];

        sr.nextBytes(salt);

        StringBuffer sb = new StringBuffer();
        for(byte b :salt) {
            sb.append(String.format("02x",b));
        }
        return sb.toString();
    }

    private String getEncrypt(String password, String salt) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

    //            System.out.println("PWD + SALT 적용 전 : " + pwd + salt);
            md.update((password + salt).getBytes());
            byte[] pwdSalt = md.digest();

            StringBuffer sb = new StringBuffer();
            for(byte b : pwdSalt) {
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();
    //            System.out.println("PWD + SALT 적용 후 : " + result);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


}
