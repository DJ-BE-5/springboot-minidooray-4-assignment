package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String accountId;
    private String email;
    private String password;
}
