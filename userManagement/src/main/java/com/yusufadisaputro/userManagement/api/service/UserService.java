package com.yusufadisaputro.userManagement.api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yusufadisaputro.userManagement.api.model.User;
import com.yusufadisaputro.userManagement.api.repository.UserRepository;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(int id){
        return userRepository.findById(id);
    }

    public User setUser(User user){
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists", ex);
        }
    }
}
