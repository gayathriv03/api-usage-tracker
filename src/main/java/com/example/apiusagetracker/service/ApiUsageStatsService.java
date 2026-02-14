package com.example.apiusagetracker.service;

import com.example.apiusagetracker.dto.ApiUsagePerApiDTO;
import com.example.apiusagetracker.dto.ApiUsagePerUserDTO;
import com.example.apiusagetracker.repository.ApiUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiUsageStatsService {

    private final ApiUsageRepository apiUsageRepository;

    public Long getTotalUsage() {
        return apiUsageRepository.getTotalUsageCount();
    }

    public List<ApiUsagePerApiDTO> getUsagePerApi() {

        List<Object[]> rows = apiUsageRepository.getUsagePerApi();

        return rows.stream()
                .map(row -> new ApiUsagePerApiDTO(
                        (String) row[0],                  // endpoint
                        ((Number) row[1]).longValue()     // count
                ))
                .toList();
    }


    public List<ApiUsagePerUserDTO> getUsagePerUser() {

        List<Object[]> rows = apiUsageRepository.getUsagePerUser();

        return rows.stream()
                .map(row -> new ApiUsagePerUserDTO(
                        (String) row[0],                  // email
                        ((Number) row[1]).longValue()     // count
                ))
                .toList();
    }
    public Long getMyTotalUsage(Long userId) {
        return apiUsageRepository.getTotalUsageByUserId(userId);
    }


}
