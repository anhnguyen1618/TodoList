package com.zozo.todolist.controllers;

import com.zozo.todolist.models.Comment;
import com.zozo.todolist.models.Task;
import com.zozo.todolist.models.User;
import com.zozo.todolist.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RequestMapping("/comments")
@RestController
public class Comments {

    @Autowired
    CommentRepository commentRepository;

    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE, produces = {MediaType.ALL_VALUE})
    @Transactional
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) {
        commentRepository.delete(commentId);
        return new ResponseEntity<>("Comment " +commentId+ " is deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> updateComment(@PathVariable("commentId") int commentId, @RequestBody Comment
            updatedComment) {
        Comment comment = commentRepository.getOne(commentId);
        if (comment == null) {
            return new ResponseEntity<>("This comment does not exist", HttpStatus.OK);
        }

        comment.setContent(updatedComment.getContent());
        commentRepository.save(comment);
        System.out.println(comment.getContent());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
