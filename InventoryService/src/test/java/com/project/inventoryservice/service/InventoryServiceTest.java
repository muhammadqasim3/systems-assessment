package com.project.inventoryservice.service;

import com.project.inventoryservice.service.InventoryService;
import com.project.inventoryservice.entity.Inventory;
import com.project.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {
    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testItemAvailability_SufficientStock() {
        Inventory inventory = new Inventory("Item1", 10);
        when(inventoryRepository.findByItemName("Item1")).thenReturn(Optional.of(inventory));

        assertTrue(inventoryService.isItemAvailable("Item1", 5));
    }

    @Test
    void testItemAvailability_InsufficientStock() {
        Inventory inventory = new Inventory("Item1", 3);
        when(inventoryRepository.findByItemName("Item1")).thenReturn(Optional.of(inventory));

        assertFalse(inventoryService.isItemAvailable("Item1", 5));
    }

    @Test
    void testItemAvailability_ItemNotFound() {
        when(inventoryRepository.findByItemName("ItemX")).thenReturn(Optional.empty());

        assertFalse(inventoryService.isItemAvailable("ItemX", 5));
    }
}

