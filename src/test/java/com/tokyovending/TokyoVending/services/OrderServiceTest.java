package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Order;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.models.User;
import com.tokyovending.TokyoVending.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private Order order;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");

        product = new Product();
        product.setName("testProduct");

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setProducts(Arrays.asList(product));
        order.setOrderDateTime(LocalDateTime.now());
    }

    @Test
    void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Order> orders = orderService.getAllOrders();
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_found() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(orderRepository, times(1)).findById(any());
    }

    @Test
    void getOrderById_notFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> orderService.getOrderById(1L));

        verify(orderRepository, times(1)).findById(any());
    }

    @Test
    void createOrder() {
        when(orderRepository.save(any())).thenReturn(order);

        Order savedOrder = orderService.createOrder(order);
        assertNotNull(savedOrder);
        assertEquals(1L, savedOrder.getId());

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void updateOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);

        Order updatedOrder = orderService.updateOrder(1L, order);
        assertNotNull(updatedOrder);
        assertEquals(1L, updatedOrder.getId());

        verify(orderRepository, times(1)).findById(any());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void updateOrder_nonExistingId_shouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> {
            orderService.updateOrder(1L, order);
        });
    }

    @Test
    void deleteOrder_existingId_shouldDeleteOrder() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrder_nonExistingId_shouldThrowException() {
        when(orderRepository.existsById(1L)).thenReturn(false);
        assertThrows(RecordNotFoundException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void exportAllOrdersToCSV() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        String csv = orderService.exportAllOrdersToCSV();
        assertTrue(csv.contains("testUser"));
        assertTrue(csv.contains("testProduct"));

        verify(orderRepository, times(1)).findAll();
    }
}



