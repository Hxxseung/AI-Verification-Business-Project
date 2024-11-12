package com.sparta.aibusinessproject.domain.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.aibusinessproject.domain.store.dto.StoreData;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreCreateRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreUpdateRequest;
import com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchListResponse;
import com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchResponse;
import com.sparta.aibusinessproject.domain.store.service.StoreService;
import com.sparta.aibusinessproject.global.exception.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("가게 생성 테스트")
    void createStore() throws Exception {
        StoreCreateRequest request = new StoreCreateRequest(
                "Test Store", "123 Test St.", "A test store", "123-456-7890", "OPEN", "creator", "modifier", "deleter", "category");

        Mockito.doNothing().when(storeService).createOrder(any(StoreCreateRequest.class));

        mockMvc.perform(post("/api/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("가게 생성에 성공하였습니다"));
    }

    @Test
    @DisplayName("가게 조회 테스트")
    void getStore() throws Exception {
        UUID storeId = UUID.randomUUID();
        StoreSearchResponse response = StoreSearchResponse.builder()
                .name("Test Store")
                .address("123 Test St.")
                .description("A test store")
                .status("OPEN")
                .build();

        Mockito.when(storeService.getStoreById(eq(storeId))).thenReturn(response);

        mockMvc.perform(get("/api/stores/{storeId}", storeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Test Store"));
    }

    @Test
    @DisplayName("모든 가게 조회 테스트")
    void getAllStores() throws Exception {
        StoreSearchListRequest request = new StoreSearchListRequest("category", "123 Test St.", 10);
        PageRequest pageable = PageRequest.of(0, 10);
        Page<StoreSearchListResponse> page = new PageImpl<>(List.of(
                StoreSearchListResponse.builder().name("Store 1").minDeliveryPrice(1000).build(),
                StoreSearchListResponse.builder().name("Store 2").minDeliveryPrice(2000).build()
        ));

        Mockito.when(storeService.getStores(any(StoreSearchListRequest.class), eq(pageable))).thenReturn(page);

        mockMvc.perform(get("/api/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name").value("Store 1"));
    }

    @Test
    @DisplayName("가게 수정 테스트")
    void updateStore() throws Exception {
        UUID storeId = UUID.randomUUID();
        StoreUpdateRequest request = new StoreUpdateRequest(
                "Updated Store", "456 Updated St.", "Updated description", "987-654-3210", "CLOSED", "modifier");
        StoreData response = StoreData.builder()
                .name("Updated Store")
                .address("456 Updated St.")
                .description("Updated description")
                .phone("987-654-3210")
                .status("CLOSED")
                .modifiedBy("modifier")
                .build();

        Mockito.when(storeService.update(eq(storeId), any(StoreUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/api/stores/{storeId}", storeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Store"));
    }

    @Test
    @DisplayName("가게 삭제 테스트")
    void deleteStore() throws Exception {
        UUID storeId = UUID.randomUUID();

        Mockito.doNothing().when(storeService).delete(storeId);

        mockMvc.perform(delete("/api/stores/{storeId}", storeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("가게 정보가 삭제되었습니다."));
    }
}
