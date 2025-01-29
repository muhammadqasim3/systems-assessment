package com.project.orderservice.controller;

import com.project.orderservice.entity.Order;
import com.project.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order API", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Place an order", description = "Places an order and checks inventory availability")
    public ResponseEntity<Order> placeOrder(@RequestParam String itemName, @RequestParam int quantity) {
        return ResponseEntity.ok(orderService.placeOrder(itemName, quantity));
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "gets all the orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}