package com.project.inventoryservice.controller;

import com.project.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private InventoryService inventoryService;

    private MockMvc mockMvc;

    private static final String ITEM_NAME = "Laptop";
    private static final int QUANTITY = 2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    void testCheckItemAvailability_ItemAvailable() throws Exception {
        // Mock service method response
        when(inventoryService.isItemAvailable(ITEM_NAME, QUANTITY)).thenReturn(true);

        // Perform GET request to check item availability
        mockMvc.perform(get("/inventory/check")
                        .param("itemName", ITEM_NAME)
                        .param("quantity", String.valueOf(QUANTITY))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // Verify service method interaction
        verify(inventoryService, times(1)).isItemAvailable(ITEM_NAME, QUANTITY);
    }

    @Test
    void testCheckItemAvailability_ItemNotAvailable() throws Exception {
        // Mock service method response
        when(inventoryService.isItemAvailable(ITEM_NAME, QUANTITY)).thenReturn(false);

        // Perform GET request to check item availability
        mockMvc.perform(get("/inventory/check")
                        .param("itemName", ITEM_NAME)
                        .param("quantity", String.valueOf(QUANTITY))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        // Verify service method interaction
        verify(inventoryService, times(1)).isItemAvailable(ITEM_NAME, QUANTITY);
    }

    @Test
    void testReduceStock_Success() throws Exception {
        // Perform POST request to reduce stock
        mockMvc.perform(post("/inventory/reduce")
                        .param("itemName", ITEM_NAME)
                        .param("quantity", String.valueOf(QUANTITY))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock reduced successfully"));

        // Verify service method interaction
        verify(inventoryService, times(1)).reduceStock(ITEM_NAME, QUANTITY);
    }
}
