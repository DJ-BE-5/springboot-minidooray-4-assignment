package com.nhn.edu.springboot.minidooray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Comment {

    @Id
    private Long commentId;
    private Long accountId;
    private String email;
    private LocalDateTime registeredTime;
    /*
    public Comment(){
    }
    public Comment(Long commentId, Long accountId, String email, LocalDateTime registeredTime){
        this.commentId = commentId;
        this.accountId = accountId;
        this.email = email;
        this.registeredTime = registeredTime;

    }*/
}
