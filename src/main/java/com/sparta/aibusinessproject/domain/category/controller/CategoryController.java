package com.sparta.aibusinessproject.domain.category.controller;


import com.sparta.aibusinessproject.domain.category.dto.request.CategoryCreateRequest;
import com.sparta.aibusinessproject.domain.category.dto.response.CategoryListResponse;
import com.sparta.aibusinessproject.domain.category.service.CategoryService;
import com.sparta.aibusinessproject.global.exception.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Response<?> addFolders(@RequestBody CategoryCreateRequest request) {
        categoryService.addCategory(request);

        return Response.success(request.name() +"의 카테고리 생성이 완료되었습니다.");
    }

    @GetMapping("/{categoryId}")
    public Response<CategoryListResponse> getCategory(@PathVariable UUID categoryId) {
        return Response.success(categoryService.getCategory(categoryId));
    }

    @GetMapping
    public Response<List<CategoryListResponse>> getAllCategories() {
        return Response.success(categoryService.getCategories());
    }

    @PatchMapping("/{categoryId}")
    public Response<CategoryListResponse> updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryCreateRequest request) {
        return Response.success(categoryService.update(categoryId,request));
    }

    @DeleteMapping("/{categoryId}")
    public Response<?> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.delete(categoryId);
        return Response.success(categoryId +"의 삭제가 완료되었습니다.");
    }
}

