package com.nhn.edu.springboot.minidooray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Account {
    @Id
    private String accountId;
    private String email;
    private String salt;
    private String saltedPassword;

}

