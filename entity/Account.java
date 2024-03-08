package com.nhn.edu.springboot.homework.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Account {

    @Id
    private String id;
    private String password;
    private String email;
    private String salt;
}
