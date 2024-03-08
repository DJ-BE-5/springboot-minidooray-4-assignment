package com.nhn.edu.springboot.minidooray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Comment {

    @Id
    private Long commentId;
    private Long accountId;
    private String email;
    private String salt;
    public Comment(){

    }
    public Comment(Long commentId, Long accountId, String email, String salt){
        this.commentId = commentId;
        this.accountId = accountId;
        this.email = email;
        this.salt = salt;

    }
}
