package com.sparta.aibusinessproject.domain.product.repository;

import com.sparta.aibusinessproject.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// 상품 관련 DB 작업을 위한 레포짙
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(String keyword, Pageable pageable); // 이름으로 상품 검색
}
