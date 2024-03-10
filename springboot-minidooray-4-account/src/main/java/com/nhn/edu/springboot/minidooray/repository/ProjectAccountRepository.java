package com.nhn.edu.springboot.minidooray.repository;

import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, Long>{

    List<ProjectAccount> findByProjectId(Long projectId);

    @Query("select pa from ProjectAccount pa where pa.account.accountId = :accountId")
    List<ProjectAccount> findByAccountId(@Param("accountId") String accountId);

    @Query("select pa from ProjectAccount pa where pa.account.accountId = :accountId and pa.projectId = :projectId")
    Optional<ProjectAccount> findByProjectIdAndAccountId(Long projectId, String accountId);
}
