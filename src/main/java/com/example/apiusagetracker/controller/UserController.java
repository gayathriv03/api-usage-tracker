package com.example.apiusagetracker.controller;

import com.example.apiusagetracker.dto.UserCreateRequest;
import com.example.apiusagetracker.entity.User;
import com.example.apiusagetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/create-user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody UserCreateRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }
}
