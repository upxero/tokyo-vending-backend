package com.tokyovending.TokyoVending.repositories;

import com.tokyovending.TokyoVending.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

