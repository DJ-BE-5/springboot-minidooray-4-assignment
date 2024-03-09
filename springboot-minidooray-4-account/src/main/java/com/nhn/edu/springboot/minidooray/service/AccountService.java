package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAccount();
    Account createAccount(Account account);
    Account getAccount(String accountId);

    List<Account> findAllAccount();

    Optional<Account> findAccountById(String AccountId);

    Account createAccount(AccountDto accountDto);

    void deleteAccount(String accountId);

}
