package com.tokyovending.TokyoVending.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    private List<ProductDto> products;
    private LocalDateTime orderDateTime;
    private boolean completed;
    private UserDto user;
    private VendingMachineDto vendingMachine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public VendingMachineDto getVendingMachine() {
        return vendingMachine;
    }

    public void setVendingMachine(VendingMachineDto vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
}

