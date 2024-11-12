package com.sparta.aibusinessproject.domain.store.service;

import com.sparta.aibusinessproject.domain.store.dto.StoreData;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreCreateRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreUpdateRequest;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import com.sparta.aibusinessproject.domain.store.repository.StoreRepository;
import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;
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
    public Page<com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchListResponse> getStores(StoreSearchListRequest searchDto, Pageable pageable) {
        return storeRepository.searchStores(searchDto, pageable);
    }

    // 가게 세부 조회
    @Transactional
    public com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchResponse getStoreById(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_STORE));

        return  com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchResponse.from(store);
    }

    @Transactional
    public StoreData update(UUID storeId, StoreUpdateRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_STORE));

        // 가게 정보 업데이트
        store.updateStoreDetails(request.name(), request.description(), request.address());

        // 명시적으로 저장 호출
        storeRepository.save(store);

        return StoreData.from(store);
    }


    // 가게 삭제
    @Transactional
    public void delete(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_STORE));

        storeRepository.delete(store);
    }
}