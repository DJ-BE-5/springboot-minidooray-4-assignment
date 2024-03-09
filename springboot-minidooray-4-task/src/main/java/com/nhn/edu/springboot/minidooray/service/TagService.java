package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.TagDto;
import com.nhn.edu.springboot.minidooray.entity.Comment;
import com.nhn.edu.springboot.minidooray.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> findAllTags();
    Optional<Tag> findTagById(Long tagId);
    Tag createTag(TagDto tagDto);
    void deleteTag(Long tagId);
}
