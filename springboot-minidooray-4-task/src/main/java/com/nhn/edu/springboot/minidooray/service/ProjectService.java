package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAllProject();
    Optional<Project> findProjectById(Long projectId);
    Optional<Project> findProjectByAccountId(Long accountId);
    Optional<Project> findProjectByTagId(Long tagId);
    Project createProject(Long projectId);

    void deleteProject(Long projectId);
}
