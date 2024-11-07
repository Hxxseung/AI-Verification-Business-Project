package com.sparta.aibusinessproject.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String status;
    private String storeName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt;
}
