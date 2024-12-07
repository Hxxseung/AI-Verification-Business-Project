package com.sparta.aibusinessproject.domain.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.aibusinessproject.domain.category.dto.request.CategoryCreateRequest;
import com.sparta.aibusinessproject.domain.category.dto.response.CategoryListResponse;
import com.sparta.aibusinessproject.domain.category.entity.Category;
import com.sparta.aibusinessproject.domain.category.service.CategoryService;
import com.sparta.aibusinessproject.global.exception.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID testCategoryId;
    private CategoryCreateRequest createRequest;
    private CategoryListResponse categoryResponse;

    @BeforeEach
    void setUp() {
        testCategoryId = UUID.randomUUID();
        createRequest = new CategoryCreateRequest("테스트 카테고리");
        categoryResponse = new CategoryListResponse(testCategoryId, "테스트 카테고리");
    }

    @Test
    @DisplayName("카테고리 생성 테스트")
    void addCategoryTest() throws Exception {
        Category category = new Category(testCategoryId, "테스트 카테고리");

        when(categoryService.addCategory(any(CategoryCreateRequest.class))).thenReturn(category);

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("테스트 카테고리의 카테고리 생성이 완료되었습니다."));

        verify(categoryService).addCategory(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("단일 카테고리 조회 테스트")
    void getCategoryTest() throws Exception {
        when(categoryService.getCategory(testCategoryId)).thenReturn(categoryResponse);

        mockMvc.perform(get("/api/category/{categoryId}", testCategoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(testCategoryId.toString()))
                .andExpect(jsonPath("$.data.name").value("테스트 카테고리"));
    }

    @Test
    @DisplayName("전체 카테고리 조회 테스트")
    void getAllCategoriesTest() throws Exception {
        CategoryListResponse category2 = new CategoryListResponse(UUID.randomUUID(), "테스트 카테고리 2");
        when(categoryService.getCategories()).thenReturn(Arrays.asList(categoryResponse, category2));

        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void updateCategoryTest() throws Exception {
        CategoryCreateRequest updateRequest = new CategoryCreateRequest("수정된 카테고리");
        CategoryListResponse updatedResponse = new CategoryListResponse(testCategoryId, "수정된 카테고리");

        when(categoryService.update(eq(testCategoryId), any(CategoryCreateRequest.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(patch("/api/category/{categoryId}", testCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("수정된 카테고리"));
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteCategoryTest() throws Exception {
        doNothing().when(categoryService).delete(testCategoryId);

        mockMvc.perform(delete("/api/category/{categoryId}", testCategoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(testCategoryId + "의 삭제가 완료되었습니다."));

        verify(categoryService).delete(testCategoryId);
    }
}
