package com.sparta.aibusinessproject.domain.store.service;

import com.sparta.aibusinessproject.domain.store.dto.StoreData;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import com.sparta.aibusinessproject.domain.store.request.StoreCreateRequest;
import com.sparta.aibusinessproject.domain.store.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.request.StoreUpdateRequest;
import com.sparta.aibusinessproject.domain.store.response.StoreSearchListResponse;
import com.sparta.aibusinessproject.domain.store.repository.StoreRepository;
import com.sparta.aibusinessproject.domain.store.response.StoreSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 가게 추가
    @Transactional
    public void createOrder(StoreCreateRequest requestDto) {
        StoreData data = StoreCreateRequest.toDto(requestDto);

        // 존재하는 가게이름인지 확인
        Optional<Store> store = storeRepository.findByName(data.name());

        // 존재하지 않다면
        if(store.isEmpty()) {
            Store storeEntity = storeRepository.save(StoreData.toEntity(data));
        }else{
            throw new ApplicationException(ErrorCode.DUPLICATED_STORENAME);
        }
    }

    // 가게 리스트 출력
    public Page<StoreSearchListResponse> getStores(StoreSearchListRequest searchDto, Pageable pageable) {
        return storeRepository.searchStores(searchDto, pageable);
    }

    // 가게 세부 조회
    @Transactional
    public StoreSearchResponse getStoreById(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_STORE));

        return  StoreSearchResponse.from(store);
    }

    // 가게 수정
    @Transactional
    public StoreData update(UUID storeId, StoreUpdateRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_STORE));

        store.update(request);

        return  StoreData.from(store);
    }

    // 가게 삭제
    @Transactional
    public void delete(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_STORE));

        storeRepository.delete(store);
    }
}