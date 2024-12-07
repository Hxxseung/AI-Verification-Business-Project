package com.sparta.aibusinessproject.domain.ai.controller;


import com.sparta.aibusinessproject.domain.ai.dto.request.AiMessageRequest;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiMessageResponse;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchListResponse;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchResponse;
import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import com.sparta.aibusinessproject.domain.ai.service.AiService;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import com.sparta.aibusinessproject.global.exception.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.rmi.server.LogStream.log;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private static final int[] ALLOWED_PAGE_SIZES = {10, 30, 50};
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final AiService service;


    @PostMapping("/message/{storeId}")
    public Response<AiMessageResponse> getMessage(@RequestBody AiMessageRequest request, @PathVariable("storeId") UUID storeId, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(storeId);
//        if (store == null) {
//            throw new IllegalArgumentException("Authenticated store is null");
//        }
        if (storeId == null ) {
            throw new IllegalArgumentException("Store ID is required");
        }
        String username = userDetails.getUsername();
        log(username);
        return Response.success(new AiMessageResponse(service.getCompletion(request.text(), username, storeId)));
    }



//    // 유저별 상세 조회
//    @GetMapping
//    public Response<List<AiSearchResponse>> getData(@AuthenticationPrincipal Ai ai) {
//        return Response.success(service.getDataFromUser(ai));
//    }
//
//    // 데이터 전부 출력
//    @PostMapping("/data")
//    public Response<Page<AiSearchListResponse>> getAllData(Pageable pageable) {
//
//        int size = DEFAULT_PAGE_SIZE; // 기본 10건
//        if(Arrays.stream(ALLOWED_PAGE_SIZES).anyMatch(s->s == pageable.getPageSize())){ //요청 사이즈가 10, 30, 50일 때
//            size = pageable.getPageSize();
//        }
//
//        Pageable validatedPageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
//
//
//        return Response.success(service.getDataList(validatedPageable));
//    }
//
//    // 가게 삭제
//    @DeleteMapping
//    public Response<?> storeDelete(@PathVariable UUID aiId, @AuthenticationPrincipal Ai ai) {
//        UUID  uuid = service.delete(aiId,ai.getStore());
//        return Response.success( uuid +"가게 정보가 삭제되었습니다.");
//    }

}