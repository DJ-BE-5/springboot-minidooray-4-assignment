package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Long taskId;
    private String title;
    private String content;
    private MileStoneDto mileStone;
}
