package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllComments();
    Optional<Comment> findCommentById(Long commentId);

    Comment createComment();
    void deleteComment(Long commentId);
}
