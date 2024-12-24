package com.assessment.kam.repository;

import com.assessment.kam.model.PerformanceMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PerformanceMetricsRepository extends JpaRepository<PerformanceMetrics, Long> {

    Optional<PerformanceMetrics> findByLeadId(Long leadId);

    List<PerformanceMetrics> findTop10ByOrderByOrderCountDesc();

    List<PerformanceMetrics> findTop10ByOrderByTotalRevenueDesc();
}

