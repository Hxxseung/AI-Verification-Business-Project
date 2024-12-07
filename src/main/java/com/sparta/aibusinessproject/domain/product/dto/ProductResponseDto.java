package com.sparta.aibusinessproject.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductResponseDto {
    private UUID id; // 상품 UUID
    private String name; // 상품 이름
    private String description; // 상품 설명
    private int price; // 상품 가격
    private String status; // 상품 상태
    private String storeName; // 스토어 이름

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt; // 생성 일자

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt; // 수정 일자
}
