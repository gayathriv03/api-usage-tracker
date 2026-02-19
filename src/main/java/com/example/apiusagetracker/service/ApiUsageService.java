package com.example.apiusagetracker.service;

import com.example.apiusagetracker.entity.Api;
import com.example.apiusagetracker.entity.ApiUsage;
import com.example.apiusagetracker.entity.User;
import com.example.apiusagetracker.exception.ApiNotFoundException;
import com.example.apiusagetracker.exception.DatabaseOperationException;
import com.example.apiusagetracker.exception.InvalidUserException;
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

        if (user == null) {
            throw new InvalidUserException("User cannot be null while recording API usage");
        }
        if (api == null) {
            throw new ApiNotFoundException("API not found while recording usage");
        }
       try {
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
       catch (Exception e) {
           throw new DatabaseOperationException(
                   "Failed to record API usage for userId="
                           + user.getId()
                           + " api="
                           + api.getEndpoint(), e
           );
       }
    }
}
