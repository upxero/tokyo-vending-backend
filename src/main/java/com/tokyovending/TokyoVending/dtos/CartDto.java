package com.tokyovending.TokyoVending.dtos;

import com.tokyovending.TokyoVending.models.User;
import com.tokyovending.TokyoVending.models.Product;

import java.util.List;

public class CartDto {
    private Long id;
    private User user;
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

