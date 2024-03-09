package com.nhn.edu.springboot.minidooray.dto;

import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;

public class ProjectAccountDto {
    public enum Auth {
        ADMIN,USER}

    private Long projectAccountId;
    private ProjectAccount.Auth auth;
    private Long projectId;
    private Account accountId;
}
