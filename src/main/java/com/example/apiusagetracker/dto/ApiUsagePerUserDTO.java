package com.example.apiusagetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiUsagePerUserDTO {
    private String email;
    private Long totalCalls;

    public ApiUsagePerUserDTO(String email, Long totalCalls) {
        this.email = email;
        this.totalCalls = totalCalls;
    }
}
