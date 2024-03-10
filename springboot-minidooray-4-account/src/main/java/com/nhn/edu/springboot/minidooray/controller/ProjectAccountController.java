package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.AccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectAccountDto;
import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;
import com.nhn.edu.springboot.minidooray.repository.AccountRepository;
import com.nhn.edu.springboot.minidooray.repository.ProjectAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectAccountController {

    //projectId 기준으로 projectAccount가져오기
    private final ProjectAccountRepository projectAccountRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public <projectAccountRepository> ProjectAccountController(ProjectAccountRepository projectAccountRepository,
                                                               AccountRepository accountRepository) {
        this.projectAccountRepository = projectAccountRepository;
        this.accountRepository = accountRepository;
    }

    //projectId기준으로 ProjectAccount 가져오는 메소드
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getProjectByProjectId(@PathVariable Long projectId) {
        List<ProjectAccount> projectAccounts = projectAccountRepository.findByProjectId(projectId);

        List<ProjectAccountDto> projectAccountDtoList = new ArrayList<>();

        for(ProjectAccount projectAccount : projectAccounts) {
            ProjectAccountDto projectAccountDto = new ProjectAccountDto();
            AccountDto accountDto = new AccountDto();
            Account account = projectAccount.getAccount();

            accountDto.setEmail(account.getEmail());
            accountDto.setAccountId(account.getAccountId());

            projectAccountDto.setProjectAccountId(projectAccount.getProjectAccountId());
            projectAccountDto.setAccountId(projectAccount.getAccount().getAccountId());
            projectAccountDto.setProjectId(projectAccount.getProjectId());
            projectAccountDto.setAuth(projectAccount.getAuth());
            projectAccountDto.setAccount(accountDto);

            projectAccountDtoList.add(projectAccountDto);
        }

        return ResponseEntity.ok(projectAccountDtoList);
    }

    //accountId기준으로 ProjectAccount 가져오는 메소드
    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getProjectByAccountId(@PathVariable String accountId) {
//        List<ProjectAccount> projectAccounts = projectAccountRepository.findAll();
        List<ProjectAccount> projectAccounts = projectAccountRepository.findByAccountId(accountId);

        List<ProjectAccountDto> projectAccountDtoList = new ArrayList<>();

        for(ProjectAccount projectAccount : projectAccounts) {
            ProjectAccountDto projectAccountDto = new ProjectAccountDto();

            projectAccountDto.setProjectAccountId(projectAccount.getProjectAccountId());
            projectAccountDto.setAccountId(projectAccount.getAccount().getAccountId());
            projectAccountDto.setProjectId(projectAccount.getProjectId());
            projectAccountDto.setAuth(projectAccount.getAuth());

            projectAccountDtoList.add(projectAccountDto);
        }

        return ResponseEntity.ok(projectAccountDtoList);
    }

    //{projectId, accountId}기준으로 ProjectAccount 삭제하는 메소드
    @DeleteMapping("/project/{projectId}/account/{accountId}")
    public ResponseEntity<?> deleteProjectAccount(@PathVariable Long projectId, @PathVariable String accountId) {
        Optional<ProjectAccount> projectAccount = projectAccountRepository.findByProjectIdAndAccountId(projectId, accountId);
        if (projectAccount.isPresent()) {
            projectAccountRepository.delete(projectAccount.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProjectAccount not found");
        }
    }

    //ProjectAccountController에 projectId, AccountId를 받으면 {projectId, AccountId, Account.Auth.USER}를 save 해주는 메소드
    @PostMapping("/project/{projectId}/account/{accountId}/save")
    public ResponseEntity<?> saveProjectAccount(@PathVariable Long projectId,
                                                @PathVariable String accountId,
                                                @RequestBody ProjectAccountDto projectAccountDto) {
        Optional<ProjectAccount> optionalProjectAccount = projectAccountRepository.findByProjectIdAndAccountId(projectId, accountId);

        if(optionalProjectAccount.isPresent()){
            return ResponseEntity.notFound().build();
        }

        ProjectAccount projectAccount = new ProjectAccount();
        projectAccount.setAccount(accountRepository.findById(accountId).get());
        projectAccount.setProjectId(projectId);
        projectAccount.setAuth(projectAccountDto.getAuth());

        projectAccountRepository.save(projectAccount);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}