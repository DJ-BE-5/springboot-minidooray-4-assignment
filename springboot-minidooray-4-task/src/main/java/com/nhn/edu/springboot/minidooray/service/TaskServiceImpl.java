package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.TaskDto;
import com.nhn.edu.springboot.minidooray.entity.MileStone;
import com.nhn.edu.springboot.minidooray.entity.Project;
import com.nhn.edu.springboot.minidooray.entity.Task;
import com.nhn.edu.springboot.minidooray.repository.MilestoneRepository;
import com.nhn.edu.springboot.minidooray.repository.ProjectRepository;
import com.nhn.edu.springboot.minidooray.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Override
    public Optional<Task> findTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> findTasksByProjectId(Long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if(optionalProject.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return optionalProject.get().getTasks();
    }

    @Override
    public Task createTask(TaskDto taskDto) {
        Task task = new Task();
        MileStone mileStone = new MileStone();

        mileStone.setStartDate(taskDto.getMileStone().getStartDate());
        mileStone.setEndDate(taskDto.getMileStone().getEndDate());

        mileStone = milestoneRepository.save(mileStone);

        task.setTitle(taskDto.getTitle());
        task.setContent(taskDto.getContent());
        task.setMileStone(mileStone);

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}