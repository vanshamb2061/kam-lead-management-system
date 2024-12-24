package com.assessment.kam.repository;

import com.assessment.kam.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    List<Interaction> findByLeadId(Long leadId);

}

