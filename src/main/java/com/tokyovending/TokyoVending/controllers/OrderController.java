package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.OrderDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Order;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.services.OrderService;
import com.tokyovending.TokyoVending.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDto> orderDtos = orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(convertToDto(order));
        } else {
            throw new RecordNotFoundException("Order with ID " + id + " not found.");
        }
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = convertToEntity(orderDto);
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto orderDto) {
        Order order = convertToEntity(orderDto);
        Order updatedOrder = orderService.updateOrder(id, order);
        if (updatedOrder != null) {
            return ResponseEntity.ok(convertToDto(updatedOrder));
        } else {
            throw new RecordNotFoundException("Order with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    private OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setProducts(order.getProducts().stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    return productDto;
                })
                .collect(Collectors.toList()));
        orderDto.setOrderDateTime(order.getOrderDateTime());
        orderDto.setCompleted(order.isCompleted());
        return orderDto;
    }

    private Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setProducts(orderDto.getProducts().stream()
                .map(productDto -> {
                    Product product = new Product();
                    product.setId(productDto.getId());
                    return product;
                })
                .collect(Collectors.toList()));
        order.setOrderDateTime(orderDto.getOrderDateTime());
        order.setCompleted(orderDto.isCompleted());
        return order;
    }
}



