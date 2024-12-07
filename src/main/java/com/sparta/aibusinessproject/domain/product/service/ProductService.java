package com.sparta.aibusinessproject.domain.product.service;

import com.sparta.aibusinessproject.domain.product.dto.ProductRequestDto;
import com.sparta.aibusinessproject.domain.product.dto.ProductResponseDto;
import com.sparta.aibusinessproject.domain.product.entity.Product;
import com.sparta.aibusinessproject.domain.product.entity.ProductStatus;
import com.sparta.aibusinessproject.domain.product.exception.ProductNotFoundException;
import com.sparta.aibusinessproject.domain.product.exception.StoreNotFoundException;
import com.sparta.aibusinessproject.domain.product.repository.ProductRepository;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import com.sparta.aibusinessproject.domain.store.repository.StoreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    // 상품 생성
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Store store = storeRepository.findById(UUID.fromString(requestDto.getStoreId()))
                .orElseThrow(StoreNotFoundException::new);

        Product product = new Product();
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setHidden(false);
        product.setStatus(ProductStatus.AVAILABLE);
        product.setStore(store);

        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    // ID로 상품 조회
    public ProductResponseDto getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(ProductNotFoundException::new);
        return convertToDto(product);
    }

    // 키워드로 상품 검색
    public Page<ProductResponseDto> searchProducts(String keyword, Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(Sort.Direction.ASC, "createdAt")
            );
        }

        Page<Product> products = productRepository.findByNameContainingAndHiddenFalse(keyword, pageable);
        return products.map(this::convertToDto);
    }

    // 소프트 삭제
    public void deleteProduct(UUID id, String userRole, String username) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        validateUserAccess(product.getStore().getStoreId(), userRole, username);

        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    // 상품 숨김 상태 업데이트
    public ProductResponseDto updateProductVisibility(UUID id, boolean isHidden, String userRole, String username) {
        Product product = productRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(ProductNotFoundException::new);

        validateUserAccess(product.getStore().getStoreId(), userRole, username);

        product.setHidden(isHidden);
        Product updatedProduct = productRepository.save(product);

        return convertToDto(updatedProduct);
    }

    // 상품 정보 업데이트
    public ProductResponseDto updateProduct(UUID id, @Valid ProductRequestDto requestDto, String userRole, String username) {
        Product product = productRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(ProductNotFoundException::new);

        validateUserAccess(product.getStore().getStoreId(), userRole, username);

        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setStatus(ProductStatus.AVAILABLE);

        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    // 사용자의 가게 접근 권한 검증
    private void validateUserAccess(UUID storeId, String userRole, String username) {
        if (userRole.equals("ADMIN")) {
            return;
        }

        UUID storeUserId = storeRepository.findById(storeId)
                .map(Store::getUserId)
                .orElseThrow(StoreNotFoundException::new);

        if (!userRole.equals("OWNER") || !storeUserId.toString().equals(username)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    // 엔티티를 DTO로 변환
    private ProductResponseDto convertToDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus().name())
                .storeName(product.getStore().getName())
                .createdAt(product.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updatedAt(product.getUpdatedAt() != null
                        ? product.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        : null)
                .build();
    }
}
