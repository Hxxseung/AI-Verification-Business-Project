package com.sparta.aibusinessproject.domain.product.controller;

import com.sparta.aibusinessproject.domain.product.dto.ProductRequestDto;
import com.sparta.aibusinessproject.domain.product.dto.ProductResponseDto;
import com.sparta.aibusinessproject.domain.product.service.ProductService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 생성 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PostMapping
    public Response<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto product = productService.createProduct(requestDto);
        return Response.success(product);
    }

    // 상품 조회 (ID로 조회)
    @GetMapping("/{id}")
    public Response<ProductResponseDto> getProduct(@PathVariable UUID id) {
        ProductResponseDto product = productService.getProductById(id);
        return Response.success(product);
    }

    // 상품 수정 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PutMapping("/{id}")
    public Response<ProductResponseDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequestDto requestDto,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        UUID userId = UUID.fromString(authentication.getName());
        ProductResponseDto updatedProduct = productService.updateProduct(id, requestDto, userRole, userId);
        return Response.success(updatedProduct);
    }

    // 상품 삭제 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @DeleteMapping("/{id}")
    public Response<Void> deleteProduct(
            @PathVariable UUID id,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        UUID userId = UUID.fromString(authentication.getName());
        productService.deleteProduct(id, userRole, userId);
        return Response.success(null); // 삭제 후 반환 값 없음
    }

    // 상품 검색 (키워드로 검색, 숨겨진 상품 제외)
    @GetMapping("/search")
    public Response<Page<ProductResponseDto>> searchProducts(
            @RequestParam String keyword,
            Pageable pageable) {
        Page<ProductResponseDto> products = productService.searchProducts(keyword, pageable);
        return Response.success(products);
    }

    // 상품 숨김 상태 변경 (ADMIN, OWNER만 접근 가능)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PatchMapping("/{id}/hide")
    public Response<ProductResponseDto> hideProduct(
            @PathVariable UUID id,
            @RequestParam boolean isHidden,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        UUID userId = UUID.fromString(authentication.getName());
        ProductResponseDto updatedProduct = productService.updateProductVisibility(id, isHidden, userRole, userId);
        return Response.success(updatedProduct);
    }
}
