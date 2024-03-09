package com.nhn.edu.springboot.minidooray.repository;

import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, Long>{

    Optional<ProjectAccount> findByProjectId(Long projectId);

    Optional<ProjectAccount> findByAccountId(Long accountId);

    Optional<ProjectAccount> findByProjectIdAndAccountId(Long projectId, Long accountId);
}
