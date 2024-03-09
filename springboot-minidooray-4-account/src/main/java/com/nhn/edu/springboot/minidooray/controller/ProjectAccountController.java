package com.nhn.edu.springboot.minidooray.controller;

import com.mysql.fabric.Response;
import com.nhn.edu.springboot.minidooray.repository.ProjectAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProjectAccountController {
    private final ProjectAccountRepository projectAccountRepository;

    @Autowired
    public ProjectAccountController(ProjectAccountRepository projectAccountRepository) {
        this.projectAccountRepository = projectAccountRepository;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectAccountId) {
        Optional<?> project = projectAccountRepository.findById(projectAccountId);
        if(project.isPresent()) {
            return ResponseEntity.ok(project.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
    }
}

