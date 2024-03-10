package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.MilestoneDto;
import com.nhn.edu.springboot.minidooray.entity.MileStone;

import java.util.List;
import java.util.Optional;

public interface MilestoneService {
    Optional<MileStone> findMilestoneByTaskId(Long taskId);
    MileStone createMilestone(MilestoneDto milestoneDto);

    void deleteMilestone(Long milestoneId);

}
