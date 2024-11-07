package com.sparta.aibusinessproject.domain.product.service;

import com.sparta.aibusinessproject.domain.product.dto.ProductRequestDto;
import com.sparta.aibusinessproject.domain.product.dto.ProductResponseDto;
import com.sparta.aibusinessproject.domain.product.entity.Product;
import com.sparta.aibusinessproject.domain.product.entity.ProductStatus;
import com.sparta.aibusinessproject.domain.product.repository.ProductRepository;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import com.sparta.aibusinessproject.domain.store.repository.StoreRepository;
import com.sparta.aibusinessproject.exception.ProductNotFoundException;
import com.sparta.aibusinessproject.exception.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

// 상품 관련 비즈니스 로직을 처리하는 서비스
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    // 상품 생성
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));

        Product product = new Product();
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setStatus(ProductStatus.valueOf(requestDto.getStatus().toUpperCase()));
        product.setStore(store);

        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    // 상품 조회 (ID로 조회)
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return convertToDto(product);
    }

    // 상품 수정
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto, String userRole, Long userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (userRole.equals("CUSTOMER")) {
            throw new AccessDeniedException("구매자는 상품을 수정할 수 없습니다.");
        } else if (userRole.equals("OWNER") && !product.getStore().getOwnerId().equals(userId)) {
            throw new AccessDeniedException("본인의 가게 상품만 수정할 수 있습니다.");
        }

        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setStatus(ProductStatus.valueOf(requestDto.getStatus().toUpperCase()));

        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    // 상품 삭제
    public void deleteProduct(Long id, String userRole, Long userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (userRole.equals("CUSTOMER")) {
            throw new AccessDeniedException("구매자는 상품을 삭제할 수 없습니다.");
        } else if (userRole.equals("OWNER") && !product.getStore().getOwnerId().equals(userId)) {
            throw new AccessDeniedException("본인의 가게 상품만 삭제할 수 있습니다.");
        }

        productRepository.deleteById(id);
    }

    // 상품 검색 (키워드로 검색)
    public Page<ProductResponseDto> searchProducts(String keyword, Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(Sort.Direction.ASC, "createdAt")
            );
        }

        Page<Product> products = productRepository.findByNameContaining(keyword, pageable);
        return products.map(this::convertToDto);
    }

    // Product 엔티티를 ProductResponseDto로 변환
    private ProductResponseDto convertToDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setName(product.getName());
        responseDto.setDescription(product.getDescription());
        responseDto.setPrice(product.getPrice());
        responseDto.setStock(product.getStock());
        responseDto.setStatus(product.getStatus().name());
        responseDto.setStoreName(product.getStore().getName());
        responseDto.setCreatedAt(product.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        responseDto.setUpdatedAt(product.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return responseDto;
    }
}
