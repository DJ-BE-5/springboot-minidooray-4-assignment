package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

@Data
public class ProjectAccountDto {
    public enum Auth {
        ADMIN,USER
    }

    private Long projectAccountId;
    private Auth auth;
    private Long projectId;
    private AccountDto account;
    private String accountId;
}
