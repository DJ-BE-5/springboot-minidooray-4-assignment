package com.nhn.edu.springboot.homework.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Comment {

    @Id
    private Long commentId;
    private Long ;
    private String email;
    private String salt;
}
