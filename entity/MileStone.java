package com.nhn.edu.springboot.homework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class MileStone {
    @Id
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
