package com.example.apiusagetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiUsagePerUserDTO {
    private String email;
    private Long count;
}
