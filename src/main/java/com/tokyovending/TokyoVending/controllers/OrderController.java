package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.OrderDto;
import com.tokyovending.TokyoVending.dtos.ProductDto;
import com.tokyovending.TokyoVending.dtos.UserDto;
import com.tokyovending.TokyoVending.dtos.VendingMachineDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Order;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.models.User;
import com.tokyovending.TokyoVending.models.VendingMachine;
import com.tokyovending.TokyoVending.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<OrderDto> orderDtos = orders.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(convertToDto(order));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = convertToEntity(orderDto);
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        Order order = convertToEntity(orderDto);
        Order updatedOrder = orderService.updateOrder(id, order);
        if (updatedOrder != null) {
            return ResponseEntity.ok(convertToDto(updatedOrder));
        } else {
            throw new RecordNotFoundException("Order met ID " + id + " niet gevonden.");
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
        orderDto.setUser(convertUserToDto(order.getUser()));
        orderDto.setVendingMachine(convertVendingMachineToDto(order.getVendingMachine()));
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
        order.setUser(convertUserToEntity(orderDto.getUser()));
        order.setVendingMachine(convertVendingMachineToEntity(orderDto.getVendingMachine()));
        return order;
    }

    private UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    private User convertUserToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        return user;
    }

    private VendingMachineDto convertVendingMachineToDto(VendingMachine vendingMachine) {
        VendingMachineDto vendingMachineDto = new VendingMachineDto();

        vendingMachineDto.setId(vendingMachine.getId());
        vendingMachineDto.setLocation(vendingMachine.getLocation());
        vendingMachineDto.setOpen(vendingMachine.isOpen());
        vendingMachineDto.setProducts(vendingMachine.getProducts());

        return vendingMachineDto;
    }

    private VendingMachine convertVendingMachineToEntity(VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = new VendingMachine();

        vendingMachine.setId(vendingMachineDto.getId());
        vendingMachine.setLocation(vendingMachineDto.getLocation());
        vendingMachine.setOpen(vendingMachineDto.isOpen());
        vendingMachine.setProducts(vendingMachineDto.getProducts());

        return vendingMachine;
    }
}





