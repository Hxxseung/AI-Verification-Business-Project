package com.sparta.aibusinessproject.domain.ai.controller;


import com.sparta.aibusinessproject.domain.ai.dto.response.AiMessageResponse;
import com.sparta.aibusinessproject.domain.ai.dto.request.AiMessageRequest;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchListResponse;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchResponse;
import com.sparta.aibusinessproject.domain.ai.service.AiService;
import com.sparta.aibusinessproject.global.exception.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private static final int[] ALLOWED_PAGE_SIZES = {10, 30, 50};
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final AiService service;


    @PostMapping
    public Response<AiMessageResponse> getMessage(@RequestBody AiMessageRequest reqeust, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return Response.success(new AiMessageResponse(service.getCompletion(reqeust.text(),userDetails.getUser())));
    }


    // 유저별 상세 조회
    @GetMapping("{productId}")
    public Response<List<AiSearchResponse>> getData(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return Response.success(service.getDataFromUser(userDetails));
    }

    // 데이터 전부 출력
    @PostMapping("/{productId}")
    public Response<Page<AiSearchListResponse>> getAllData(Pageable pageable) {

        int size = DEFAULT_PAGE_SIZE; // 기본 10건
        if(Arrays.stream(ALLOWED_PAGE_SIZES).anyMatch(s->s == pageable.getPageSize())){ //요청 사이즈가 10, 30, 50일 때
            size = pageable.getPageSize();
        }

        Pageable validatedPageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());


        return Response.success(service.getDataList(validatedPageable));
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public Response<?> storeDelete(@PathVariable UUID aiId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID  uuid = service.delete(aiId,userDetails.getUser());
        return Response.success( uuid +"가게 정보가 삭제되었습니다.");
    }

}