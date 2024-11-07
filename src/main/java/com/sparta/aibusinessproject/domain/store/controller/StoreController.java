package com.sparta.aibusinessproject.domain.store.controller;


import com.sparta.aibusinessproject.domain.store.dto.StoreRequestDto;
import com.sparta.aibusinessproject.domain.store.dto.StoreResponseDto;
import com.sparta.aibusinessproject.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;


    // 가게 등록
    @PostMapping
    public StoreResponseDto createStore(@RequestBody @Valid StoreRequestDto requestDto) {
        return storeService.createStore(requestDto);
    }

    // 가게 단건 조회
    @GetMapping("/{storeId}")
    public StoreResponseDto getStore(@PathVariable UUID storeId) {
        return storeService.getStoreById(storeId);
    }

    //가게 정보 수정
    @PutMapping("/{storeId}")
    public StoreResponseDto updateStore(@PathVariable UUID storeId, @RequestBody @Valid StoreRequestDto requestDto) {
        return storeService.updateStore(requestDto);
    }

    //가게 삭제
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable UUID storeId,@RequestBody @Valid StoreRequestDto requestDto ,Authentication authentication) {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        String userId = authentication.getName();
        return storeService.deleteStore(requestDto);
    }
}
