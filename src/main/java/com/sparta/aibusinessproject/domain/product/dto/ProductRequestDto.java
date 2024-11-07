package com.sparta.aibusinessproject.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @Positive(message = "Stock must be greater than zero")
    private int stock;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Store ID is required")
    private Long storeId;
}
