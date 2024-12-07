package com.sparta.aibusinessproject.domain.product.repository;

import com.sparta.aibusinessproject.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByNameContainingAndHiddenFalse(String keyword, Pageable pageable);
}
