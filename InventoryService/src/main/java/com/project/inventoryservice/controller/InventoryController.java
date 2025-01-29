package com.project.inventoryservice.controller;

import com.project.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory API", description = "Endpoints for managing inventory stock levels")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/check")
    @Operation(summary = "Check item availability", description = "Checks if an item is available in stock")
    public ResponseEntity<Boolean> checkItemAvailability(@RequestParam String itemName, @RequestParam int quantity) {
        boolean available = inventoryService.isItemAvailable(itemName, quantity);
        return ResponseEntity.ok(available);
    }

    @PostMapping("/reduce")
    @Operation(summary = "Reduce inventory stock", description = "Reduces stock when an order is placed")
    public ResponseEntity<String> reduceStock(@RequestParam String itemName, @RequestParam int quantity) {
        inventoryService.reduceStock(itemName, quantity);
        return ResponseEntity.ok("Stock reduced successfully");
    }
}
