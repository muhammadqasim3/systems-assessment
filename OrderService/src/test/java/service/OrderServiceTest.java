package service;

import com.project.orderservice.entity.Order;
import com.project.orderservice.repository.OrderRepository;
import com.project.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    private static final String ITEM_NAME = "Laptop";
    private static final int QUANTITY = 2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder_ItemAvailable() {
        // Mock inventory service response
        ResponseEntity<Map> response = mock(ResponseEntity.class);
        Map<String, Object> responseBody = Collections.singletonMap("available", true);
        when(response.getBody()).thenReturn(responseBody);
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenReturn(response);

        // Mock order repository save
        Order order = new Order();
        order.setItemName(ITEM_NAME);
        order.setQuantity(QUANTITY);
        order.setStatus("CONFIRMED");
        order.setOrderDate(LocalDateTime.now());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Call the service method
        Order placedOrder = orderService.placeOrder(ITEM_NAME, QUANTITY);

        // Verify the result
        assertNotNull(placedOrder);
        assertEquals(ITEM_NAME, placedOrder.getItemName());
        assertEquals(QUANTITY, placedOrder.getQuantity());
        assertEquals("CONFIRMED", placedOrder.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testPlaceOrder_ItemNotAvailable() {
        // Mock inventory service response
        ResponseEntity<Map> response = mock(ResponseEntity.class);
        Map<String, Object> responseBody = Collections.singletonMap("available", true);
        when(response.getBody()).thenReturn(responseBody);
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenReturn(response);

        // Call the service method and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(ITEM_NAME, QUANTITY);
        });

        // Verify exception message
        assertEquals("Item is out of stock", exception.getMessage());
    }

    @Test
    void testGetAllOrders() {
        // Mock order repository response
        Order order = new Order();
        order.setItemName(ITEM_NAME);
        order.setQuantity(QUANTITY);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        // Call the service method
        List<Order> orders = orderService.getAllOrders();

        // Verify the result
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(ITEM_NAME, orders.get(0).getItemName());
        assertEquals(QUANTITY, orders.get(0).getQuantity());
    }
}

