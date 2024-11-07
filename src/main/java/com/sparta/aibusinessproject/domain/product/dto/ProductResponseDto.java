package com.sparta.aibusinessproject.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id; // 상품 ID
    private String name; // 상품 이름
    private String description; // 상품 설명
    private double price; // 상품 가격
    private int stock; // 상품 재고
    private String status; // 상품 상태
    private String storeName; // 스토어 이름

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt; // 생성 일자

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt; // 수정 일자
}
