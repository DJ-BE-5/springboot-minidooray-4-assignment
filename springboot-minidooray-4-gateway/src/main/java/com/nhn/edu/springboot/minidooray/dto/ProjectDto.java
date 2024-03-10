package com.nhn.edu.springboot.minidooray.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    public enum Status {
        ACTIVATED, DORMANCY, TERMINATED
    };

    private Long projectId;
    private String title;
    private Status status;
    private List<AccountDto> accounts;
    private List<TaskDto> tasks;
    private List<TagDto> tags;
}
