package com.zozo.todolist.controllers;

import com.zozo.todolist.models.Task;
import com.zozo.todolist.models.User;
import com.zozo.todolist.repositories.TaskRepository;
import com.zozo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("/users")
@RestController
public class Users {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll() {
        List<User> users = userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.ALL_VALUE})
//    @Transactional
//    public ResponseEntity<?> delete(@PathVariable("id") int id) {
//        Task foundTask = taskRepository.findOne(id);
//        if (foundTask == null) {
//            return new ResponseEntity<>("This task does not exist", HttpStatus.NOT_FOUND);
//        }
//        taskRepository.delete(id);
//        return new ResponseEntity<>("task " + id + " is deleted now", HttpStatus.OK);
//    }
}
