package com.nhn.edu.springboot.minidooray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Task {
    @Id
    private Long id;
    private String title;
    private String content;

    @OneToOne
    private MileStone mileStone;
}
