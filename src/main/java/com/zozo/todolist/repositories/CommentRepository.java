package com.zozo.todolist.repositories;

import com.zozo.todolist.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
