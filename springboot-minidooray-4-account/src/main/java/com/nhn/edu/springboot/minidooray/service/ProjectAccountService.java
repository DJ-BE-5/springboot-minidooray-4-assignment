package com.nhn.edu.springboot.minidooray.service;


import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;

import java.util.List;
import java.util.Optional;

public interface ProjectAccountService {

    List<ProjectAccount> getProjectAccount();
    ProjectAccount createProjectAccount(ProjectAccount projectAccount);
    ProjectAccount getProjectAccount(Long projectAccountId);
    void deleteProjectAccount(Long projectAccountId);
}
