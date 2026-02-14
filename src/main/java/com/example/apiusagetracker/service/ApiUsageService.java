package com.example.apiusagetracker.service;

import com.example.apiusagetracker.entity.Api;
import com.example.apiusagetracker.entity.ApiUsage;
import com.example.apiusagetracker.entity.User;
import com.example.apiusagetracker.repository.ApiUsageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ApiUsageService {

    private final ApiUsageRepository apiUsageRepository;

    public ApiUsageService(ApiUsageRepository apiUsageRepository) {
        this.apiUsageRepository = apiUsageRepository;
    }

    @Transactional
    public void recordUsage(User user, Api api) {

        Optional<ApiUsage> existingUsage =
                apiUsageRepository.findByUserAndApi(user, api);

        if (existingUsage.isPresent()) {
            ApiUsage usage = existingUsage.get();
            usage.setCount(usage.getCount() + 1);
            usage.setCalledAt(LocalDateTime.now());
        } else {
            ApiUsage usage = new ApiUsage();
            usage.setUser(user);
            usage.setApi(api);
            usage.setCount(1);
            usage.setCalledAt(LocalDateTime.now());
            apiUsageRepository.save(usage);
        }
    }
}
