package com.zozo.todolist.controllers;

import com.zozo.todolist.models.User;
import com.zozo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RequestMapping("/auth")
@RestController
public class Auth {
    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> register(@RequestBody User user) {
        User matchedUser = userRepository.findOne(user.getUsername());
        if (matchedUser != null) {
            return new ResponseEntity<>("This username already exists", HttpStatus.OK);
        }
        user.encodePassword();
        User newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody User user) {
        User matchedUser = userRepository.findOne(user.getUsername());
        if (matchedUser == null) {
            return new ResponseEntity<>("Login failed!", HttpStatus.UNAUTHORIZED);
        }

        boolean result = matchedUser.comparePassword(user.getPassword());
        return new ResponseEntity<>(matchedUser, HttpStatus.OK);
    }
}
