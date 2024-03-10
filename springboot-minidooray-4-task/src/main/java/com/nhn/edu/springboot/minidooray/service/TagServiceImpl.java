package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.TagDto;
import com.nhn.edu.springboot.minidooray.entity.Tag;
import com.nhn.edu.springboot.minidooray.entity.Task;
import com.nhn.edu.springboot.minidooray.repository.TagRepository;
import com.nhn.edu.springboot.minidooray.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Optional<Tag> findTagById(Long tagId) {
        return tagRepository.findById(tagId);
    }

    @Override
    public List<Tag> findTagsByTaskId(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if(optionalTask.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return optionalTask.get().getTags();
    }

    @Override
    public Tag createTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setTitle(tagDto.getTitle());

        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
