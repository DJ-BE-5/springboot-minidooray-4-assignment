package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long commentId;
    private Long accountId;
    private String email;
    private LocalDateTime registeredTime;
}
