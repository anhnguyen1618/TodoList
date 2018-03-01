package com.zozo.todolist.controllers;

import com.zozo.todolist.models.User;
import com.zozo.todolist.repositories.UserRepository;
import com.zozo.todolist.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RequestMapping("/auth")
@RestController
public class Auth {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> register(@RequestBody User user) {
        User matchedUser = userRepository.findOne(user.getUsername());
        if (matchedUser != null) {
            return new ResponseEntity<>("This username already exists", HttpStatus.CONFLICT);
        }
        user.encodePassword();
        user.setRole("USER");
        User newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(HttpServletResponse response, @RequestBody User user) {

        TokenAuthenticationService.authenticateOnLogin(authenticationManager, user, response);

        User matchedUser = userRepository.findOne(user.getUsername());
        return new ResponseEntity<>(matchedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> logOut(HttpServletRequest request) {

        TokenAuthenticationService.invalidateToken(request);
        return new ResponseEntity<>("User is logged out!", HttpStatus.OK);
    }


}
