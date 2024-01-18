package com.yusufadisaputro.userManagement.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.yusufadisaputro.userManagement.api.model.User;
import com.yusufadisaputro.userManagement.api.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping("/v1/user")
    public ResponseEntity<?> getUser(@RequestParam("id") String id){
        int idNumber = Integer.parseInt(id);
        Optional<User> user = userService.getUser(idNumber);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/v1/user")
    public ResponseEntity<?> setUser(@RequestBody User user){
        try {
            User savedUser = userService.setUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }
}
