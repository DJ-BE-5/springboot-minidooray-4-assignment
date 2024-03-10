package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.entity.Project;
import com.nhn.edu.springboot.minidooray.repository.ProjectRepository;
import com.nhn.edu.springboot.minidooray.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity getProjectByProjectId(@PathVariable("projectId") Long projectId) {
        Optional<Project> project = projectService.findProjectById(projectId);

        if(project.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(project);
    }

    @PostMapping("/create")
    public ResponseEntity createProject(@RequestBody ProjectDto projectDto) {
        Project project = projectService.createProject(projectDto);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/modify/{projectId}")
    public ResponseEntity modifyProject(@PathVariable("projectId") Long projectId,
                                        @RequestBody ProjectDto projectDto) {
        Optional<Project> optionalProject = projectService.findProjectById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setTitle(projectDto.getTitle());

            projectRepository.save(project);
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity deleteProjectByProjectId(@PathVariable("projectId") Long projectId) {
        projectService.deleteProjectByProjectId(projectId);
        return ResponseEntity.ok().build();
    }
}

