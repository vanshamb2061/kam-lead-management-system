package com.assessment.kam.service;

import com.assessment.kam.dto.OrderDTO;
import com.assessment.kam.factory.OrderFactory;
import com.assessment.kam.listener.OrderEventListener;
import com.assessment.kam.model.Lead;
import com.assessment.kam.model.Order;
import com.assessment.kam.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventListener orderEventListener;

    @Autowired
    private LeadService leadService;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Lead lead;
        Optional<Lead> leadOptional = leadService.getLeadByID(orderDTO.getLeadId());
        if (leadOptional.isPresent()) {
            lead = leadOptional.get();
        } else {
            throw new RuntimeException("Lead not found with id: " + orderDTO.getLeadId());
        }
        Order order = OrderFactory.createOrder(orderDTO.getOrderDate(), orderDTO.getTotalAmount(), lead);
        Order savedOrder = orderRepository.save(order);
        orderEventListener.onOrderPlaced(orderDTO.getLeadId(), savedOrder.getId(), orderDTO.getTotalAmount(), orderDTO.getOrderDate());
        return  convertToDTO(savedOrder);
    }

    public Iterable<OrderDTO> getOrdersByLeadId(Long leadId) {
        Iterable<Order> orders = orderRepository.findByLeadId(leadId);
        return StreamSupport.stream(orders.spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    public OrderDTO convertToDTO(Order order){
        return new OrderDTO(order.getOrderDate(),order.getTotalAmount(),order.getLead().getId());
    }
}
