package com.sparta.aibusinessproject.domain.store.controller;


import com.sparta.aibusinessproject.domain.store.dto.StoreData;
import com.sparta.aibusinessproject.domain.store.exception.Response;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreCreateRequest;
import com.sparta.aibusinessproject.domain.store.dto.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.service.StoreService;
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
        storeService.createOrder(request);
        return  Response.success("가게 생성에 성공하였습니다");
    }

    // 가게 상세 조회
    @GetMapping("/{storeId}")
    public Response<com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchResponse> getStore(@PathVariable UUID storeId) {
        return Response.success(storeService.getStoreById(storeId));
    }

    // 가게 리스트 전부 출력
    @GetMapping
    public Response<Page<com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchListResponse>> getAllStores(@RequestBody StoreSearchListRequest searchDto, Pageable pageable) {
        return Response.success(storeService.getStores(searchDto,pageable));
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
