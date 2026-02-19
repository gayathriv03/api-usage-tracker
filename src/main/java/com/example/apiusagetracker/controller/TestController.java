package com.example.apiusagetracker.controller;

import com.example.apiusagetracker.exception.ApiNotFoundException;
import com.example.apiusagetracker.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class TestController {

    private final ApiRepository apiRepository;

    @GetMapping("/api1")
    public String api1()
    {
        if (apiRepository.findByEndpoint("/api1") == null) {
            throw new ApiNotFoundException("API /api1 not registered in system");
        }
        return "API 1: Success";
    }

    @GetMapping("/api2")
    public String api2()
    {
        if (apiRepository.findByEndpoint("/api2") == null) {
            throw new ApiNotFoundException("API /api2 not registered in system");
        }
        return "API 2: Success";
    }
}
