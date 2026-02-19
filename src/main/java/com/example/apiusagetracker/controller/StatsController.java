package com.example.apiusagetracker.controller;

import com.example.apiusagetracker.dto.ApiUsageLogDTO;
import com.example.apiusagetracker.dto.ApiUsagePerApiDTO;
import com.example.apiusagetracker.dto.ApiUsagePerUserDTO;
import com.example.apiusagetracker.exception.InvalidUserHeaderException;
import com.example.apiusagetracker.exception.UserNotFoundException;
import com.example.apiusagetracker.service.ApiUsageStatsService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final ApiUsageStatsService statsService;

    @GetMapping("/total")
    public Long totalUsage() {
        return statsService.getTotalUsage();
    }

    @GetMapping("/per-api")
    public List<ApiUsagePerApiDTO> usagePerApi() {
        return statsService.getUsagePerApi();
    }

    @GetMapping("/per-user")
    public Page<ApiUsagePerUserDTO> usagePerUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       return statsService.getUsagePerUser(page, size);
    }

    @GetMapping("/logs")
    public Page<ApiUsageLogDTO> getLogs(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(required = false) String endpoint,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "calledAt") String sortBy
    ) {
        return statsService.getLogs(
                userId, endpoint, from, to, page, size, sortBy);
    }

    @GetMapping("/me")
    public Map<String, Object> getMyStats(
            @RequestHeader("X-USER-ID") Long userId
    ) {

        if (userId == null) {
            throw new InvalidUserHeaderException("X-USER-ID header is missing");
        }

        if (userId <= 0) {
            throw new InvalidUserHeaderException("Invalid user id");
        }
        Long total = statsService.getMyTotalUsage(userId);

        return Map.of(
                "userId", userId,
                "totalCalls", total == null ? 0 : total
        );

    }
}

