package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;
import com.nhn.edu.springboot.minidooray.repository.AccountRepository;
import com.nhn.edu.springboot.minidooray.repository.ProjectAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ProjectAccountServicelmpl implements ProjectAccountService{
    private final ProjectAccountRepository projectAccountRepository;

    public ProjectAccountServicelmpl(ProjectAccountRepository projectAccountRepository) {
        this.projectAccountRepository = projectAccountRepository;
    }

    @Override
    public List<ProjectAccount> getProjectAccount() {
        return projectAccountRepository.findAll();
    }

    @Override
    @Transactional
    public ProjectAccount createProjectAccount(ProjectAccount projectAccount) {
        boolean present = projectAccountRepository.findById(projectAccount.getProjectAccountId()).isPresent();
        if(present) throw new IllegalArgumentException("already exist" + projectAccount.getAccountId());

        return projectAccountRepository.save(projectAccount);
    }


    @Override
    @Transactional(readOnly = true)
    public ProjectAccount getProjectAccount(Long projectAccountId) {
        return projectAccountRepository.findById(projectAccountId).orElseThrow();

    }

    @Override
    public void deleteProjectAccount(Long projectAccountId) {
        projectAccountRepository.deleteById(projectAccountId);

    }
}
