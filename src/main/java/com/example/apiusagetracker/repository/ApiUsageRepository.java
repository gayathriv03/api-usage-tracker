package com.example.apiusagetracker.repository;


import com.example.apiusagetracker.entity.Api;
import com.example.apiusagetracker.entity.ApiUsage;
import com.example.apiusagetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
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

    //for pagination
    @Query(
            value = """
        SELECT usr.email AS email, SUM(u.count) AS totalCalls
        FROM api_usage u
        JOIN users usr ON u.user_id = usr.id
        GROUP BY usr.email
        ORDER BY totalCalls DESC
    """,
            countQuery = """
        SELECT COUNT(DISTINCT usr.email)
        FROM api_usage u
        JOIN users usr ON u.user_id = usr.id
    """,
            nativeQuery = true
    )
    Page<Object[]> getUsagePerUser(Pageable pageable);

    @Query("select coalesce(sum(u.count),0) from ApiUsage u where u.user.id = :userId")
    Long getTotalUsageByUserId(@Param("userId") Long userId);

//    @Query("""
//SELECT u
//FROM ApiUsage u
//JOIN u.api a
//JOIN u.user usr
//WHERE usr.id = :userId
//""")
//    Page<ApiUsage> findLogsWithJoins(
//            @Param("userId") Long userId,
//            Pageable pageable
//    );

    @Query("""
SELECT u
FROM ApiUsage u
JOIN u.api a
JOIN u.user usr
WHERE usr.id = :userId
AND (:endpoint IS NULL OR a.name = :endpoint)
AND (:from IS NULL OR u.calledAt >= :from)
AND (:to IS NULL OR u.calledAt <= :to)
""")
    Page<ApiUsage> findLogsWithFilters(
            Long userId,
            String endpoint,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);








}
