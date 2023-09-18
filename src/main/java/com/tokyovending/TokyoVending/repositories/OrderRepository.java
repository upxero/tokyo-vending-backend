package com.tokyovending.TokyoVending.repositories;

import com.tokyovending.TokyoVending.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

