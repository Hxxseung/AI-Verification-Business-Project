package com.sparta.aibusinessproject.domain.product.controller;

import com.sparta.aibusinessproject.domain.product.dto.ProductRequestDto;
import com.sparta.aibusinessproject.domain.product.dto.ProductResponseDto;
import com.sparta.aibusinessproject.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// 상품 관리를 위한 컨트롤러
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 생성 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }

    // 상품 조회 (ID로 조회)
    @GetMapping("/{id}")
    public ProductResponseDto getProduct(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    // 상품 수정 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequestDto requestDto,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        UUID userId = UUID.fromString(authentication.getName());
        return productService.updateProduct(id, requestDto, userRole, userId);
    }

    // 상품 삭제 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable UUID id,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        UUID userId = UUID.fromString(authentication.getName());
        productService.deleteProduct(id, userRole, userId);
    }

    // 상품 검색 (키워드로 검색, 숨겨진 상품 제외)
    @GetMapping("/search")
    public Page<ProductResponseDto> searchProducts(
            @RequestParam String keyword,
            Pageable pageable) {
        return productService.searchProducts(keyword, pageable);
    }

    // 상품 숨김 상태 변경 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PatchMapping("/{id}/hide")
    public ProductResponseDto hideProduct(
            @PathVariable UUID id,
            @RequestParam boolean isHidden,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        UUID userId = UUID.fromString(authentication.getName());
        return productService.updateProductVisibility(id, isHidden, userRole, userId);
    }
}
