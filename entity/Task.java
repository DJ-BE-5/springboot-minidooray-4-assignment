package com.nhn.edu.springboot.homework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
