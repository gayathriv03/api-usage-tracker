package com.example.apiusagetracker.repository;

import com.example.apiusagetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
