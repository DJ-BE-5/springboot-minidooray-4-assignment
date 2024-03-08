package com.nhn.edu.springboot.minidooray.milestone;

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
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
