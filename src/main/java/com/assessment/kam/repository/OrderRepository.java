package com.assessment.kam.repository;

import com.assessment.kam.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByLeadId(Long leadId);
}
