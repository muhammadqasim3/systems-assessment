package controller;

import com.project.orderservice.controller.OrderController;
import com.project.orderservice.entity.Order;
import com.project.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private static final String ITEM_NAME = "Laptop";
    private static final int QUANTITY = 2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        // Mock the service method response
        Order order = new Order();
        order.setItemName(ITEM_NAME);
        order.setQuantity(QUANTITY);
        when(orderService.placeOrder(ITEM_NAME, QUANTITY)).thenReturn(order);

        // Call the controller method
        ResponseEntity<Order> response = orderController.placeOrder(ITEM_NAME, QUANTITY);

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ITEM_NAME, response.getBody().getItemName());
        assertEquals(QUANTITY, response.getBody().getQuantity());
    }

    @Test
    void testGetAllOrders() {
        // Mock the service method response
        Order order = new Order();
        order.setItemName(ITEM_NAME);
        order.setQuantity(QUANTITY);
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order));

        // Call the controller method
        ResponseEntity<List<Order>> response = orderController.getAllOrders();

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(ITEM_NAME, response.getBody().get(0).getItemName());
    }
}

