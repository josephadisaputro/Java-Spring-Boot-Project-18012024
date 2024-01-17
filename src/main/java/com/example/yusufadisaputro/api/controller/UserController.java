package com.example.yusufadisaputro.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.yusufadisaputro.api.model.User;
import com.example.yusufadisaputro.api.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping("/v1/user")
    public User getUser(@RequestParam int id){
        return userService.getUser(id)
                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping("/v1/user")
    public ResponseEntity<User> setUser(@RequestBody User user){
        User savedUser = userService.setUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
