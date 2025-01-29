package com.project.orderservice.service;

import com.project.orderservice.entity.Order;
import com.project.orderservice.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Order placeOrder(String itemName, int quantity) {
        // Check inventory service for availability
        String inventoryUrl = "http://localhost:8081/inventory/check?itemName=" + itemName + "&quantity=" + quantity;
        ResponseEntity<Map> response = restTemplate.getForEntity(inventoryUrl, Map.class);

        boolean isAvailable = (boolean) response.getBody().get("available");
        if (!isAvailable) {
            throw new RuntimeException("Item is out of stock");
        }

        // If available, create order
        Order order = new Order();
        order.setItemName(itemName);
        order.setQuantity(quantity);
        order.setStatus("CONFIRMED");
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}