package com.sparta.aibusinessproject.domain.category.entity;

import com.sparta.aibusinessproject.domain.category.dto.request.CategoryCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_category")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;

    private String name;

    public void update(CategoryCreateRequest request) {
        this.name = request.name() != null ? request.name() : this.name;
    }


}
