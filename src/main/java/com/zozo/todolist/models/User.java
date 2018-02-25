package com.zozo.todolist.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private String role;

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void encodePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.setPassword(encoder.encode(password));
    }

    public boolean comparePassword(String comparedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(comparedPassword, password);
    }
}


