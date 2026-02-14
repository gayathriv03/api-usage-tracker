package com.example.apiusagetracker.repository;

import com.example.apiusagetracker.entity.Api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<Api,Long> {
    Api findByEndpoint(String endpoint);
}
