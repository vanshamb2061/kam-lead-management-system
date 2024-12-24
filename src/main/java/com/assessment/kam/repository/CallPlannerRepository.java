package com.assessment.kam.repository;

import com.assessment.kam.model.CallPlanner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CallPlannerRepository extends JpaRepository<CallPlanner, Long> {
    Optional<CallPlanner> findByLeadId(Long leadId);
    List<CallPlanner> findByNextCallDate(LocalDate nextCallDate);
}

