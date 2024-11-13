package com.sparta.aibusinessproject.domain.category.service;

import com.sparta.aibusinessproject.domain.category.dto.request.CategoryCreateRequest;
import com.sparta.aibusinessproject.domain.category.dto.response.CategoryListResponse;
import com.sparta.aibusinessproject.domain.category.entity.Category;
import com.sparta.aibusinessproject.domain.category.repository.CategoryRepository;
import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private UUID testCategoryId;
    private CategoryCreateRequest createRequest;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCategoryId = UUID.randomUUID();
        createRequest = new CategoryCreateRequest("테스트 카테고리");
        category = new Category(testCategoryId, "테스트 카테고리");
    }

    @Test
    void addCategoryTest() {
        when(categoryRepository.existsByName(createRequest.name())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.addCategory(createRequest);

        assertEquals("테스트 카테고리", result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void addCategoryDuplicatedTest() {
        when(categoryRepository.existsByName(createRequest.name())).thenReturn(true);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            categoryService.addCategory(createRequest);
        });

        assertEquals(ErrorCode.DUPLICATED_CATEGORY, exception.getErrorCode());
    }

    @Test
    void getCategoryTest() {
        when(categoryRepository.findById(testCategoryId)).thenReturn(Optional.of(category));

        CategoryListResponse result = categoryService.getCategory(testCategoryId);

        assertNotNull(result);
       // assertEquals(testCategoryId, result.id());
        assertEquals("테스트 카테고리", result.name());
    }

    @Test
    void getCategoryNotFoundTest() {
        when(categoryRepository.findById(testCategoryId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            categoryService.getCategory(testCategoryId);
        });

        assertEquals(ErrorCode.NOTFOUND_CATEGORY, exception.getErrorCode());
    }

    @Test
    void updateCategoryTest() {
        when(categoryRepository.findById(testCategoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByName(createRequest.name())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryListResponse result = categoryService.update(testCategoryId, createRequest);

        assertEquals("테스트 카테고리", result.name());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void deleteCategoryTest() {
        when(categoryRepository.findById(testCategoryId)).thenReturn(Optional.of(category));

        categoryService.delete(testCategoryId);

        verify(categoryRepository).delete(category);
    }

    @Test
    void deleteCategoryNotFoundTest() {
        when(categoryRepository.findById(testCategoryId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            categoryService.delete(testCategoryId);
        });

        assertEquals(ErrorCode.NOTFOUND_CATEGORY, exception.getErrorCode());
    }
}
