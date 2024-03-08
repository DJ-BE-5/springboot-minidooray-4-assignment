package com.nhn.edu.springboot.minidooray.repository;

import com.nhn.edu.springboot.minidooray.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
