package com.nhn.edu.springboot.minidooray.dto;

import com.nhn.edu.springboot.minidooray.entity.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    private Long projectId;
    private String title;
    private Project.Status status;
    private List<TaskDto> tasks;
}
