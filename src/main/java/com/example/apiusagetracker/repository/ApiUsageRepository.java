package com.example.apiusagetracker.repository;


import com.example.apiusagetracker.entity.Api;
import com.example.apiusagetracker.entity.ApiUsage;
import com.example.apiusagetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApiUsageRepository extends JpaRepository<ApiUsage,Long> {
    Optional<ApiUsage> findByUserAndApi(User user, Api api);

    @Query(
            value = "SELECT SUM(count) FROM api_usage",
            nativeQuery = true
    )
    Long getTotalUsageCount();


    @Query(
            value = """
        SELECT a.endpoint, SUM(u.count)
        FROM api_usage u
        JOIN apis a ON u.api_id = a.id
        GROUP BY a.endpoint
    """,
            nativeQuery = true
    )
    List<Object[]> getUsagePerApi();

    @Query(
            value = """
        SELECT usr.email, SUM(u.count)
        FROM api_usage u
        JOIN users usr ON u.user_id = usr.id
        GROUP BY usr.email
    """,
            nativeQuery = true
    )
    List<Object[]> getUsagePerUser();

    @Query(
            value = """
        SELECT SUM(count)
        FROM api_usage
        WHERE user_id = :userId
    """,
            nativeQuery = true
    )
    Long getTotalUsageByUserId(Long userId);




}
