package com.yusufadisaputro.userManagement.api.repository;

import com.yusufadisaputro.userManagement.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
