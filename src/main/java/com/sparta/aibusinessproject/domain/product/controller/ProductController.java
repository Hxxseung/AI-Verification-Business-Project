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

    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PostMapping
    public Response<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto product = productService.createProduct(requestDto);
        return Response.success(product);
    }

    @GetMapping("/{id}")
    public Response<ProductResponseDto> getProduct(@PathVariable UUID id) {
        ProductResponseDto product = productService.getProductById(id);
        return Response.success(product);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PutMapping("/{id}")
    public Response<ProductResponseDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequestDto requestDto,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        String username = authentication.getName(); // Username 사용
        ProductResponseDto updatedProduct = productService.updateProduct(id, requestDto, userRole, username);
        return Response.success(updatedProduct);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @DeleteMapping("/{id}")
    public Response<Void> deleteProduct(
            @PathVariable UUID id,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        String username = authentication.getName(); // Username 사용
        productService.deleteProduct(id, userRole, username);
        return Response.success(null);
    }

    @GetMapping("/search")
    public Response<Page<ProductResponseDto>> searchProducts(
            @RequestParam String keyword,
            Pageable pageable) {
        Page<ProductResponseDto> products = productService.searchProducts(keyword, pageable);
        return Response.success(products);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PatchMapping("/{id}/hide")
    public Response<ProductResponseDto> hideProduct(
            @PathVariable UUID id,
            @RequestParam boolean isHidden,
            Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        String username = authentication.getName(); // Username 사용
        ProductResponseDto updatedProduct = productService.updateProductVisibility(id, isHidden, userRole, username);
        return Response.success(updatedProduct);
    }
}
