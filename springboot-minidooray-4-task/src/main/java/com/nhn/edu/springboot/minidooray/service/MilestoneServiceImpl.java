package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.MilestoneDto;
import com.nhn.edu.springboot.minidooray.entity.MileStone;
import com.nhn.edu.springboot.minidooray.entity.Task;
import com.nhn.edu.springboot.minidooray.repository.CommentRepository;
import com.nhn.edu.springboot.minidooray.repository.MilestoneRepository;
import com.nhn.edu.springboot.minidooray.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;

    public MilestoneServiceImpl(MilestoneRepository milestoneRepository,
                              TaskRepository taskRepository) {
        this.milestoneRepository = milestoneRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<MileStone> findMilestoneByTaskId(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return Optional.of(task.getMileStone());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public MileStone createMilestone(MilestoneDto milestoneDto) {
        MileStone milestone = new MileStone();
        milestone.setStartDate(milestoneDto.getStartDate());
        milestone.setEndDate(milestoneDto.getEndDate());

        return milestoneRepository.save(milestone);
    }

    @Override
    public void deleteMilestone(Long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }
}