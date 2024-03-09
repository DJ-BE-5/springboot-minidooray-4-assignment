package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAllTasks();

    Optional<Task> findTaskById(Long taskId);
    Optional<Task> findTaskByAccountId(Long accountId);

    Task createTask();

    void deleteTask(Long taskId);

}
