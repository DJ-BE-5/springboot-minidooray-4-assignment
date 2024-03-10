package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDto {
    private Long taskId;
    private String title;
    private String content;
    private MilestoneDto mileStone;
}
