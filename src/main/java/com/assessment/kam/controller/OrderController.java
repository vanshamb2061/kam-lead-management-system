package com.assessment.kam.controller;

import com.assessment.kam.dto.OrderDTO;
import com.assessment.kam.model.Order;
import com.assessment.kam.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint to create a new order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    // Endpoint to get all orders for a lead
    @GetMapping("/lead/{leadId}")
    public Iterable<OrderDTO> getOrdersByLeadId(@PathVariable Long leadId) {
        return orderService.getOrdersByLeadId(leadId);
    }
}
