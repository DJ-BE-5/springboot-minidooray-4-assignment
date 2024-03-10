package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> findProjectById(Long projectId);
    Project createProject(ProjectDto projectDto);
    void deleteProjectByProjectId(Long projectId);
}
