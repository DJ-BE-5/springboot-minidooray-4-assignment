package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.TaskDto;
import com.nhn.edu.springboot.minidooray.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<Task> findTaskById(Long taskId);
    List<Task> findTasksByProjectId(Long projectId);
    Task createTask(TaskDto taskDto);
    void deleteTask(Long taskId);
}
