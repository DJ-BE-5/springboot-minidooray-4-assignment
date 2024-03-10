package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.CommentDto;
import com.nhn.edu.springboot.minidooray.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findCommentById(Long commentId);
    List<Comment> findCommentsByTaskId(Long taskId);
    Comment createComment(CommentDto commentDto);
    void deleteComment(Long commentId);
}
