package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.TagDto;
import com.nhn.edu.springboot.minidooray.entity.Tag;
import com.nhn.edu.springboot.minidooray.entity.Task;
import com.nhn.edu.springboot.minidooray.repository.TaskRepository;
import com.nhn.edu.springboot.minidooray.service.TagService;
import com.nhn.edu.springboot.minidooray.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/project/{projectId}/tag")
    public ResponseEntity getTagsByTaskId(@PathVariable("projectId") Long projectId) {
        // projectId를 사용하여 해당 Project에 속한 Tag 목록을 조회합니다.
        List<Tag> tags = tagService.findTagsByTaskId(projectId);
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/task/{taskId}/tag/delete/{tagId}")
    public ResponseEntity deleteByTagId(@PathVariable("taskId") Long taskId,
                                        @PathVariable("tagId") Long tagId) {
        Task task = taskService.findTaskById(taskId).get();
        Tag tag = tagService.findTagById(tagId).get();
        List<Tag> tags = task.getTags();
        tags.remove(tag);

        taskRepository.save(task);
        tagService.deleteTag(tagId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tag/create/{taskId}")
    public ResponseEntity createTag(@PathVariable("taskId") Long taskId,
                                    @RequestBody TagDto tagDto) {
        Tag tag = tagService.createTag(tagDto);
        Task task = taskService.findTaskById(taskId).get();

        List<Tag> tags = task.getTags();
        tags.add(tag);
        task.setTags(tags);

        taskRepository.save(task);

        return ResponseEntity.ok().build();
    }
}
