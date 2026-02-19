package com.example.apiusagetracker.controller;

import com.example.apiusagetracker.dto.UserCreateRequest;
import com.example.apiusagetracker.entity.User;
import com.example.apiusagetracker.exception.BadRequestException;
import com.example.apiusagetracker.exception.DatabaseOperationException;
import com.example.apiusagetracker.exception.DuplicateUserException;
import com.example.apiusagetracker.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/create-user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public User createUser(@Valid @RequestBody UserCreateRequest request) {

        if (request == null) {
            throw new BadRequestException("Request body is missing");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Name is required");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email is required");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateUserException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create user");
        }
    }
}
