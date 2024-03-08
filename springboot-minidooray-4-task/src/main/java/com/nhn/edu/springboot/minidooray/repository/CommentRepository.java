package com.nhn.edu.springboot.minidooray.repository;

import com.nhn.edu.springboot.minidooray.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
