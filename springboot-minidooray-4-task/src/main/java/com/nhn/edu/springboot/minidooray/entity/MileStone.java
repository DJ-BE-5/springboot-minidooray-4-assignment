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
public class MileStone {
    @Id
    private Long milestoneId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public MileStone(){

    }
    public MileStone(Long milestoneId, LocalDateTime startDate, LocalDateTime endDate){
        this.milestoneId = milestoneId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
