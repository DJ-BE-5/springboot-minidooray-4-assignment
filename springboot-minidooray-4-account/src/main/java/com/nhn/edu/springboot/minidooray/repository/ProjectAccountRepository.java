package com.nhn.edu.springboot.minidooray.repository;

import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, Long>{
}
