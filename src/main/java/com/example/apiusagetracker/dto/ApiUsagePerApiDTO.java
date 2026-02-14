package com.example.apiusagetracker.dto;

public record ApiUsagePerApiDTO(
        String endpoint,
        Long count
) {}
