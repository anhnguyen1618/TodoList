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

    @RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody User user) {
        User matchedUser = userRepository.findByUsername(user.getUsername());
        if (matchedUser == null) {
            return new ResponseEntity<>("This user does not exist", HttpStatus.NOT_FOUND);
        }

        User savedUsers = userRepository.save(user);

        return new ResponseEntity<>(savedUsers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> remove(@RequestBody User user) {
        User matchedUser = userRepository.findByUsername(user.getUsername());
        if (matchedUser == null) {
            return new ResponseEntity<>("This user does not exist", HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user);

        return new ResponseEntity<>("User is deleted", HttpStatus.OK);
    }


}
