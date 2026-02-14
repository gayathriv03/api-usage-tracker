package com.example.apiusagetracker.config;

import com.example.apiusagetracker.entity.Api;
import com.example.apiusagetracker.entity.User;
import com.example.apiusagetracker.repository.ApiRepository;
import com.example.apiusagetracker.repository.UserRepository;
import com.example.apiusagetracker.service.ApiUsageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiUsageInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final ApiRepository apiRepository;
    private final ApiUsageService apiUsageService;

    public ApiUsageInterceptor(UserRepository userRepository,
                               ApiRepository apiRepository,
                               ApiUsageService apiUsageService) {
        this.userRepository = userRepository;
        this.apiRepository = apiRepository;
        this.apiUsageService = apiUsageService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String endpoint = request.getRequestURI();


        if (endpoint.startsWith("/error")) {
            return true;
        }

        String userIdHeader = request.getHeader("X-USER-ID");

        if (userIdHeader == null) {
            return true;
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdHeader);
        } catch (NumberFormatException e) {
            return true;
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return true;
        }


        Api api = apiRepository.findByEndpoint(endpoint);
        if (api == null) {
            return true;
        }


        apiUsageService.recordUsage(user, api);

        return true;
    }
}
