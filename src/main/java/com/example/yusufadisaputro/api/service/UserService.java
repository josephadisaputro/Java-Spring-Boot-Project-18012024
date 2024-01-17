package com.example.yusufadisaputro.api.service;

import org.springframework.stereotype.Service;
import com.example.yusufadisaputro.api.model.User;
import com.example.yusufadisaputro.api.repository.UserRepository;
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
        return userRepository.save(user);
    }
}
