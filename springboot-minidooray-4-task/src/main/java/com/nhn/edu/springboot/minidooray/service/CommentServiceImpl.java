package com.nhn.edu.springboot.minidooray.service;

import com.nhn.edu.springboot.minidooray.dto.CommentDto;
import com.nhn.edu.springboot.minidooray.entity.Comment;
import com.nhn.edu.springboot.minidooray.entity.Task;
import com.nhn.edu.springboot.minidooray.repository.CommentRepository;
import com.nhn.edu.springboot.minidooray.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Comment> findCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public List<Comment> findCommentsByTaskId(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return task.getComments();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Comment createComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setAccountId(commentDto.getAccountId());
        comment.setContent(commentDto.getContent());
        comment.setRegisteredTime(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
