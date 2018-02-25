package com.zozo.todolist.controllers;

import com.zozo.todolist.models.Task;
import com.zozo.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/tasks")
@RestController
public class Tasks {

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll() {
        List<Task> tasks = taskRepository.findAll();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> save(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Task task) {
        Task foundTask = taskRepository.findOne(id);
        if (foundTask == null) {
            return new ResponseEntity<>("This task does not exist", HttpStatus.NOT_FOUND);
        }

        task.setId(id);
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.ALL_VALUE})
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Task foundTask = taskRepository.findOne(id);
        if (foundTask == null) {
            return new ResponseEntity<>("This task does not exist", HttpStatus.NOT_FOUND);
        }
        taskRepository.delete(id);
        return new ResponseEntity<>("task " + id + " is deleted now", HttpStatus.OK);
    }
}
