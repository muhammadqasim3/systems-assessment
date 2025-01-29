package com.project.inventoryservice.service;


import com.project.inventoryservice.entity.Inventory;
import com.project.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean isItemAvailable(String itemName, int quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByItemName(itemName);
        return inventory.isPresent() && inventory.get().getStock() >= quantity;
    }
    public void reduceStock(String itemName, int quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByItemName(itemName);
        if (inventory.isPresent() && inventory.get().getStock() >= quantity) {
            Inventory item = inventory.get();
            item.setStock(item.getStock() - quantity);
            inventoryRepository.save(item);
        } else {
            throw new RuntimeException("Insufficient stock for " + itemName);
        }
    }
}