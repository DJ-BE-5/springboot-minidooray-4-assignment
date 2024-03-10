package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;
import com.nhn.edu.springboot.minidooray.repository.ProjectAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProjectAccountController {

    //projectId 기준으로 projectAccount가져오기
    private final ProjectAccountRepository projectAccountRepository;

    @Autowired
    public <projectAccountRepository> ProjectAccountController(ProjectAccountRepository projectAccountRepository) {
        this.projectAccountRepository = (ProjectAccountRepository) projectAccountRepository;
    }

    //projectId기준으로 ProjectAccount 가져오는 메소드
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getProjectByProjectId(@PathVariable Long projectId) {
        Optional<ProjectAccount> projectAccount = projectAccountRepository.findByProjectId(projectId);
        if (projectAccount.isPresent()) {
            return ResponseEntity.ok(projectAccount.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
    }

    //accountId기준으로 ProjectAccount 가져오는 메소드
    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getProjectByAccountId(@PathVariable Long accountId) {
        Optional<ProjectAccount> projectAccount = projectAccountRepository.findByAccountId(accountId);
        if (projectAccount.isPresent()) {
            return ResponseEntity.ok(projectAccount.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
    }

    //{projectId, accountId}기준으로 ProjectAccount 삭제하는 메소드
    @DeleteMapping("/project/{projectId}/account/{accountId}")
    public ResponseEntity<?> deleteProjectAccount(@PathVariable Long projectId, @PathVariable Long accountId) {
        Optional<ProjectAccount> projectAccount = projectAccountRepository.findByProjectIdAndAccountId(projectId, accountId);
        if (projectAccount.isPresent()) {
            projectAccountRepository.delete(projectAccount.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProjectAccount not found");
        }
    }

    //ProjectAccountController에 projectId, AccountId를 받으면 {projectId, AccountId, Account.Auth.USER}를 save 해주는 메소드
    @PostMapping("/project/{projectid}/account/{accountId}/save")
    public ResponseEntity<?> saveProjectAccount(@PathVariable Long projectAccountId, @PathVariable Long projectid) {
        ProjectAccount projectAccount = new ProjectAccount(projectAccountId, ProjectAccount.Auth.USER, projectid);
        projectAccountRepository.save(projectAccount);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}