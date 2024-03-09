package com.nhn.edu.springboot.minidooray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;


import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class ProjectAccount {
    public enum Auth {
        ADMIN,USER}

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long projectAccountId;
    private Auth auth;
    private Long projectId;

    @ManyToOne
    private Account accountId;
}
