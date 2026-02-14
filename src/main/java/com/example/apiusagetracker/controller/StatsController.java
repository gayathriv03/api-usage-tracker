package com.example.apiusagetracker.controller;

import com.example.apiusagetracker.dto.ApiUsagePerApiDTO;
import com.example.apiusagetracker.dto.ApiUsagePerUserDTO;
import com.example.apiusagetracker.service.ApiUsageStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<ApiUsagePerUserDTO> usagePerUser() {
        return statsService.getUsagePerUser();
    }

    @GetMapping("/me")
    public Map<String, Object> getMyStats(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        Long total = statsService.getMyTotalUsage(userId);

        return Map.of(
                "userId", userId,
                "totalCalls", total == null ? 0 : total
        );
    }
}

