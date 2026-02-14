package com.example.apiusagetracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiUsageInterceptor apiUsageInterceptor;

    public WebConfig(ApiUsageInterceptor apiUsageInterceptor) {
        this.apiUsageInterceptor = apiUsageInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiUsageInterceptor)
                .excludePathPatterns( "/login", "/users");
    }
}
