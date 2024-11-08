package com.sparta.aibusinessproject.domain.product.service;

import com.sparta.aibusinessproject.domain.product.dto.ProductRequestDto;
import com.sparta.aibusinessproject.domain.product.dto.ProductResponseDto;
import com.sparta.aibusinessproject.domain.product.entity.Product;
import com.sparta.aibusinessproject.domain.product.entity.ProductStatus;
import com.sparta.aibusinessproject.domain.product.exception.ProductNotFoundException;
import com.sparta.aibusinessproject.domain.product.exception.StoreNotFoundException;
import com.sparta.aibusinessproject.domain.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new StoreNotFoundException("Store not found with id: " + requestDto.getStoreId()));

        Product product = new Product();
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setHidden(false); // 기본 숨김 상태: false
        product.setStatus(ProductStatus.AVAILABLE); // 기본 상태 설정
        product.setStore(store);

        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    // 상품 조회 (ID로 조회)
    public ProductResponseDto getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return convertToDto(product);
    }

    // 상품 검색 (숨겨진 상품 제외)
    public Page<ProductResponseDto> searchProducts(String keyword, Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(Sort.Direction.ASC, "createdAt")
            );
        }

        Page<Product> products = productRepository.findByNameContainingAndIsHiddenFalse(keyword, pageable);
        return products.map(this::convertToDto);
    }

    // 상품 삭제
    public void deleteProduct(UUID id, String userRole, UUID userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (!product.getStore().getMember().getUserId().equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        productRepository.deleteById(id);
    }

    // 상품 숨김 상태 업데이트
    public ProductResponseDto updateProductVisibility(UUID id, boolean isHidden, String userRole, UUID userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        // 권한 체킹
        if (!userRole.equals("ADMIN") && (!userRole.equals("OWNER") || !product.getStore().getMember().getUserId().equals(userId))) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        product.setHidden(isHidden);
        Product updatedProduct = productRepository.save(product);

        return convertToDto(updatedProduct);
    }

    // 상품 정보 업데이트
    public ProductResponseDto updateProduct(UUID id, @Valid ProductRequestDto requestDto, String userRole, UUID userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        // 권한 확인
        if (!userRole.equals("ADMIN") && (!userRole.equals("OWNER") || !product.getStore().getMember().getUserId().equals(userId))) {
            throw new AccessDeniedException("본인의 가게 상품만 수정할 수 있습니다.");
        }

        // 상품 정보 업데이트
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setStatus(ProductStatus.AVAILABLE); // 기본 값으로 AVAILABLE 하게 설정

        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    private ProductResponseDto convertToDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getProductId());
        responseDto.setName(product.getName());
        responseDto.setDescription(product.getDescription());
        responseDto.setPrice(product.getPrice());
        responseDto.setStatus(product.getStatus().name());
        responseDto.setStoreName(product.getStore().getName());
        responseDto.setCreatedAt(product.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        responseDto.setUpdatedAt(product.getUpdatedAt() != null
                ? product.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : null);
        return responseDto;
    }
}
