package com.sparta.aibusinessproject.domain.store.service;

import com.sparta.aibusinessproject.domain.store.dto.StoreData;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreCreateRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreUpdateRequest;
import com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchListResponse;
import com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchResponse;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import com.sparta.aibusinessproject.domain.store.repository.StoreRepository;
import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("가게 생성 성공 테스트")
    void createStore() {
        StoreCreateRequest request = new StoreCreateRequest(
                "Test Store", "123 Test St.", "A test store", "123-456-7890", "OPEN", "creator", "modifier", "deleter", "category");
        StoreData storeData = StoreCreateRequest.toDto(request);

        when(storeRepository.findByName(storeData.name())).thenReturn(Optional.empty());
        when(storeRepository.save(any(Store.class))).thenReturn(StoreData.toEntity(storeData));

        assertDoesNotThrow(() -> storeService.createOrder(request));
        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    @DisplayName("가게 생성 실패 - 중복된 이름")
    void createStore_DuplicateName() {
        StoreCreateRequest request = new StoreCreateRequest(
                "Test Store", "123 Test St.", "A test store", "123-456-7890", "OPEN", "creator", "modifier", "deleter", "category");
        Store store = StoreData.toEntity(StoreCreateRequest.toDto(request));

        when(storeRepository.findByName(request.name())).thenReturn(Optional.of(store));

        ApplicationException exception = assertThrows(ApplicationException.class, () -> storeService.createOrder(request));
        assertEquals(ErrorCode.DUPLICATED_STORENAME, exception.getErrorCode());
    }

    @Test
    @DisplayName("가게 조회 성공 테스트")
    void getStoreById() {
        UUID storeId = UUID.randomUUID();
        Store store = Store.builder()
                .storeId(storeId)
                .name("Test Store")
                .address("123 Test St.")
                .description("A test store")
                .status("OPEN")
                .build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        StoreSearchResponse response = storeService.getStoreById(storeId);

        assertNotNull(response);
        assertEquals("Test Store", response.name());
        verify(storeRepository, times(1)).findById(storeId);
    }

    @Test
    @DisplayName("가게 목록 조회 테스트")
    void getStores() {
        StoreSearchListRequest request = new StoreSearchListRequest("category", "123 Test St.", 10);
        PageRequest pageable = PageRequest.of(0, 10);
        Page<StoreSearchListResponse> page = new PageImpl<>(List.of(
                StoreSearchListResponse.builder().name("Store 1").minDeliveryPrice(1000).build(),
                StoreSearchListResponse.builder().name("Store 2").minDeliveryPrice(2000).build()
        ));

        when(storeRepository.searchStores(any(StoreSearchListRequest.class), eq(pageable))).thenReturn(page);

        Page<StoreSearchListResponse> result = storeService.getStores(request, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals("Store 1", result.getContent().get(0).name());
        verify(storeRepository, times(1)).searchStores(any(StoreSearchListRequest.class), eq(pageable));
    }

    @Test
    @DisplayName("가게 업데이트 테스트")
    void updateStore() {
        UUID storeId = UUID.randomUUID();
        StoreUpdateRequest request = new StoreUpdateRequest(
                "Updated Store", "456 Updated St.", "Updated description", "987-654-3210", "CLOSED", "modifier");
        Store store = Store.builder()
                .storeId(storeId)
                .name("Test Store")
                .address("123 Test St.")
                .description("A test store")
                .status("OPEN")
                .build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        StoreData updatedData = storeService.update(storeId, request);

        assertEquals("Updated Store", updatedData.name());
        assertEquals("456 Updated St.", updatedData.address());
        verify(storeRepository, times(1)).findById(storeId);
    }

    @Test
    @DisplayName("가게 삭제 테스트")
    void deleteStore() {
        UUID storeId = UUID.randomUUID();
        Store store = Store.builder()
                .storeId(storeId)
                .name("Test Store")
                .address("123 Test St.")
                .description("A test store")
                .status("OPEN")
                .build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        doNothing().when(storeRepository).delete(store);

        assertDoesNotThrow(() -> storeService.delete(storeId));
        verify(storeRepository, times(1)).delete(store);
    }
}
