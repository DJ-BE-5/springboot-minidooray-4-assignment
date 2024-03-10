package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long commentId;
    private String accountId;
    private LocalDateTime registeredTime;
    private Long taskId;
}
