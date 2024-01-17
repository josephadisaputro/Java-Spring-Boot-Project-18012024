package com.example.yusufadisaputro.api.repository;

import com.example.yusufadisaputro.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
