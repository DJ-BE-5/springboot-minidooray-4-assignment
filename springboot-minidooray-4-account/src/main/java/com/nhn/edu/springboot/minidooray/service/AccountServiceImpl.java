package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccount() {
        return accountRepository.findAll();
    }
    @Override
    @Transactional(readOnly=true)
    public Account getAccount(String accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElse(null);
    }

    @Override
    public List<Account> findAllAccount() {
        return null;
    }

    @Override
    public Optional<Account> findAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Account createAccount(AccountDto accountDto) {
        return null;
    }

    @Override
    @Transactional
    public Account createAccount(Account account){
        boolean present = accountRepository.findById(account.getAccountId()).isPresent();
        if(present) throw new IllegalArgumentException("already exist" + account.getAccountId());

        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(String accountId){
        accountRepository.deleteById(accountId);

    }
}
