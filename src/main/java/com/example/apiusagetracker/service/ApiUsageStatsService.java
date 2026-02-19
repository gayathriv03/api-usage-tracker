package com.example.apiusagetracker.service;

import com.example.apiusagetracker.dto.ApiUsageLogDTO;
import com.example.apiusagetracker.dto.ApiUsagePerApiDTO;
import com.example.apiusagetracker.dto.ApiUsagePerUserDTO;
import com.example.apiusagetracker.entity.ApiUsage;
import com.example.apiusagetracker.exception.InvalidUserHeaderException;
import com.example.apiusagetracker.repository.ApiUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
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


    public Page<ApiUsagePerUserDTO> getUsagePerUser(int page, int size) {

        size = Math.min(size, 50);

        Pageable pageable = PageRequest.of(page, size);

        Page<Object[]> rows = apiUsageRepository.getUsagePerUser(pageable);

        return rows.map(row -> new ApiUsagePerUserDTO(
                (String) row[0],
                ((Number) row[1]).longValue()
        ));
    }

    public Long getMyTotalUsage(Long userId) {
        return apiUsageRepository.getTotalUsageByUserId(userId);
    }

    public Page<ApiUsageLogDTO> getLogs(Long userId, String endpoint, LocalDateTime from, LocalDateTime to,
                                        int page, int size, String sortBy) {

        if (userId == null || userId <= 0) {
            throw new InvalidUserHeaderException("Invalid X-USER-ID");
        }

        size = Math.min(size, 50);

        List<String> allowedSort =  List.of("calledAt", "count", "api.name");

        if (!allowedSort.contains(sortBy)) {
            sortBy = "calledAt";
        }

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<ApiUsage> logs = apiUsageRepository.findLogsWithFilters(
                userId, endpoint, from, to, pageable);

        return logs.map(u -> new ApiUsageLogDTO(
                u.getApi().getName(),
                u.getUser().getEmail(),
                u.getCalledAt(),
                u.getCount() ) );
    }



}
