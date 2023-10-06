package com.tokyovending.TokyoVending.repositories;

import com.tokyovending.TokyoVending.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Username(String username);
}

