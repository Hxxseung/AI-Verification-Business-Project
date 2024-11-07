package com.sparta.aibusinessproject.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductRequestDto {

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name; // 상품 이름

    @NotBlank(message = "상품 설명은 필수입니다.")
    private String description; // 상품 설명

    @Positive(message = "가격은 0보다 커야 합니다.")
    private double price; // 상품 가격

    @Positive(message = "재고는 0보다 커야 합니다.")
    private int stock; // 상품 재고

    @NotBlank(message = "상태 값은 필수입니다.")
    private String status; // 상품 상태 (AVAILABLE, OUT_OF_STOCK 등)

    @NotNull(message = "스토어 ID는 필수입니다.")
    private Long storeId; // 연결된 스토어 ID
}
