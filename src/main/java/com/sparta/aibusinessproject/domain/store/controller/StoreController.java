package com.sparta.aibusinessproject.domain.store.controller;


import com.sparta.aibusinessproject.domain.store.dto.StoreData;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreCreateRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.service.StoreService;
import com.sparta.aibusinessproject.global.exception.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;


    // 가게 생성
    @PostMapping
    public Response<?> store(@RequestBody StoreCreateRequest request) {
        try {
            // 가게 생성하고 storeId를 반환받음
            UUID storeId = storeService.createOrder(request);

            // storeId 포함하여 성공 메시지 반환
            return Response.success("가게 생성에 성공하였습니다. 생성된 storeId: " + storeId);
        } catch (Exception e) {
            // 예외가 발생하면 에러 메시지 반환
            return Response.error("가게 생성에 실패하였습니다. " + e.getMessage());
        }
    }

    // 전체 가게 조회
    @GetMapping
    public Response<Page<StoreData>> getAllStores(Pageable pageable) {
        Page<StoreData> stores = storeService.getAllStores(pageable);
        return Response.success(stores);
    }

    // 가게 조회
    @GetMapping("/{storeId}")
    public Response<com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchResponse> getStore(@PathVariable UUID storeId) {
        return Response.success(storeService.getStoreById(storeId));
    }

    // 가게 수정
    @PatchMapping("/{storeId}")
    public Response<StoreData> storeUpdate(@PathVariable UUID storeId , @RequestBody com.sparta.aibusinessproject.domain.store.dto.request.StoreUpdateRequest request){
        return  Response.success(storeService.update(storeId,request));
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public Response<?> storeDelete(@PathVariable UUID storeId) {
        storeService.delete(storeId);
        return Response.success("가게 정보가 삭제되었습니다.");
    }
}
