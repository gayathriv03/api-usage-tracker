package com.example.apiusagetracker.config;

import com.example.apiusagetracker.entity.Api;
import com.example.apiusagetracker.entity.User;
import com.example.apiusagetracker.exception.ApiNotFoundException;
import com.example.apiusagetracker.exception.BadRequestException;
import com.example.apiusagetracker.exception.DatabaseOperationException;
import com.example.apiusagetracker.exception.InvalidUserException;
import com.example.apiusagetracker.repository.ApiRepository;
import com.example.apiusagetracker.repository.UserRepository;
import com.example.apiusagetracker.service.ApiUsageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ApiUsageInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final ApiRepository apiRepository;
    private final ApiUsageService apiUsageService;
    private static final Logger log =
            LoggerFactory.getLogger(ApiUsageInterceptor.class);

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
        request.setAttribute("startTime", System.currentTimeMillis());

        String userIdHeader = request.getHeader("X-USER-ID");

        log.info("API START {} {} userId={}",
                request.getMethod(),
                endpoint,
                userIdHeader);



        if (endpoint.startsWith("/error") ||
                endpoint.startsWith("/create-user") ||
                endpoint.startsWith("/swagger") ||
                endpoint.startsWith("/stats") ||
                endpoint.startsWith("/v3/api-docs")) {
            return true;
        }




        if (userIdHeader == null) {
            throw new BadRequestException("Missing X-USER-ID header");   // ⭐ ADDED
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdHeader);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid user id format");     // ⭐ ADDED
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new InvalidUserException("User not found with id " + userId));   // ⭐ ADDED



        Api api = apiRepository.findByEndpoint(endpoint);
        if (api == null) {
            throw new ApiNotFoundException("API not registered: " + endpoint);  // ⭐ ADDED
        }


        try {
            apiUsageService.recordUsage(user, api);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to record API usage"); // ⭐ ADDED
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        Long startTime = (Long) request.getAttribute("startTime");

        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;

            String userId = request.getHeader("X-USER-ID");

            log.info("API END {} {} userId={} status={} time={}ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    userId,
                    response.getStatus(),
                    duration);
        }
    }

}
