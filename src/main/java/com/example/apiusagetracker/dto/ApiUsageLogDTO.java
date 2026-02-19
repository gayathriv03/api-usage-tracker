package com.example.apiusagetracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiUsageLogDTO {

    private String apiName;
    private String userEmail;
    private LocalDateTime calledAt;
    private int count;

    public ApiUsageLogDTO(String apiName, String userEmail,
                          LocalDateTime calledAt, int count) {
        this.apiName = apiName;
        this.userEmail = userEmail;
        this.calledAt = calledAt;
        this.count = count;
    }
}
