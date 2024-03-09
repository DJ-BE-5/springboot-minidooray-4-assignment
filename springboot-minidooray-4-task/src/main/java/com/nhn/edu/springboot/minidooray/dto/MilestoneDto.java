package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MilestoneDto {
    private Long milestoneId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
