package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.CommentDto;
import com.nhn.edu.springboot.minidooray.entity.Comment;
import com.nhn.edu.springboot.minidooray.entity.Task;
import com.nhn.edu.springboot.minidooray.repository.TaskRepository;
import com.nhn.edu.springboot.minidooray.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create/task/{taskId}")
    public ResponseEntity createComment(@RequestBody CommentDto commentDto,
                                        @PathVariable("taskId") Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        Comment comment = commentService.createComment(commentDto);

        List<Comment> comments = task.getComments();
        comments.add(comment);

        task.setComments(comments);
        taskRepository.save(task);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/modify/{commentId}")
    public ResponseEntity modifyComment(@RequestBody CommentDto commentDto,
                                        @PathVariable("commentId") Long commentId) {
        Optional<Comment> optionalComment = commentService.findCommentById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            comment.setAccountId(commentDto.getAccountId());
            comment.setRegisteredTime(LocalDateTime.now());
            comment.setContent(commentDto.getContent());

            commentService.createComment(commentDto);
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{commentId}/task/{taskId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId,
                                        @PathVariable("taskId") Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        List<Comment> comments = task.getComments();
        comments.remove(commentService.findCommentById(commentId).get());
        task.setComments(comments);

        taskRepository.save(task);
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}