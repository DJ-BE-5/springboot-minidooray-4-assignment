package com.nhn.edu.springboot.minidooray.repository;

import com.nhn.edu.springboot.minidooray.entity.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<MileStone, Long> {
}
