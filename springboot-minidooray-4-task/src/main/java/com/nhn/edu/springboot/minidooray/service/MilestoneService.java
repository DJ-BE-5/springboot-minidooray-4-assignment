package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.entity.MileStone;

import java.util.List;
import java.util.Optional;

public interface MilestoneService {

    List<MileStone> findAllMilestone();
    Optional<MileStone> findMilestoneById(Long milestoneId);
    MileStone createMilestone(Long milestoneId);

    void deleteMilestone(Long milestoneId);

}
