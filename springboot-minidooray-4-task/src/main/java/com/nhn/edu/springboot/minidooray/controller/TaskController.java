package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.TaskDto;
import com.nhn.edu.springboot.minidooray.entity.MileStone;
import com.nhn.edu.springboot.minidooray.entity.Project;
import com.nhn.edu.springboot.minidooray.entity.Task;
import com.nhn.edu.springboot.minidooray.repository.ProjectRepository;
import com.nhn.edu.springboot.minidooray.repository.TaskRepository;
import com.nhn.edu.springboot.minidooray.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/{taskId}")
    public ResponseEntity getTaskByTaskId(@PathVariable("taskId") Long taskId) {
        Optional<Task> optionalTask = taskService.findTaskById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/modify/{taskId}")
    public ResponseEntity modifyTask(@PathVariable("taskId") Long taskId, @RequestBody TaskDto taskDto) {
        Optional<Task> optionalTask = taskService.findTaskById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setContent(taskDto.getContent());

            task = taskRepository.save(task);

            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create/project/{projectId}")
    public ResponseEntity createTask(@RequestBody TaskDto taskDto,
                                     @PathVariable("projectId") Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        Task task = new Task();

        MileStone mileStone = new MileStone();
        mileStone.setStartDate(taskDto.getMileStone().getStartDate());
        mileStone.setEndDate(taskDto.getMileStone().getEndDate());

        task = taskService.createTask(taskDto);

        List<Task> tasks = project.getTasks();
        tasks.add(task);

        project.setTasks(tasks);

        projectRepository.save(project);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity deleteTaskByTaskId(@PathVariable("taskId") Long taskId) {
        List<Project> projects = projectRepository.findAll();
        Task task = taskService.findTaskById(taskId).get();

        for(Project project : projects) {
            if(project.getTasks().contains(task)) {
                List<Task> tasks = project.getTasks();
                tasks.remove(task);

                project.setTasks(tasks);
                projectRepository.save(project);
            }
        }

        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}
