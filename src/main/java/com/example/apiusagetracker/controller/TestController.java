package com.example.apiusagetracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api1")
    public String api1()
    {
        return "API 1: Success";
    }

    @GetMapping("/api2")
    public String api2()
    {
        return "API 2: Success";
    }
}
