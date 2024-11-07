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

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDto requestDto,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        Long userId = Long.valueOf(authentication.getName());
        return productService.updateProduct(id, requestDto, userRole, userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        Long userId = Long.valueOf(authentication.getName());
        productService.deleteProduct(id, userRole, userId);
    }

    @GetMapping("/search")
    public Page<ProductResponseDto> searchProducts(
            @RequestParam String keyword,
            Pageable pageable) {
        return productService.searchProducts(keyword, pageable);
    }
}
