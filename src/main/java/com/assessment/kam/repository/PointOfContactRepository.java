package com.assessment.kam.repository;

import com.assessment.kam.model.PointOfContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointOfContactRepository extends JpaRepository<PointOfContact, Long> {
}
