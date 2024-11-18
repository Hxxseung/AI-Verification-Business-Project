package com.sparta.aibusinessproject.domain.ai.service;


import com.sparta.aibusinessproject.domain.ai.dto.AiData;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchListResponse;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchResponse;
import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import com.sparta.aibusinessproject.domain.ai.gemini.GeminiInterface;
import com.sparta.aibusinessproject.domain.ai.gemini.GeminiRequest;
import com.sparta.aibusinessproject.domain.ai.gemini.GeminiResponse;
import com.sparta.aibusinessproject.domain.ai.repository.AiRepository;
import com.sparta.aibusinessproject.domain.member.entity.Member;
import com.sparta.aibusinessproject.domain.member.repository.MemberRepository;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import com.sparta.aibusinessproject.domain.store.repository.StoreRepository;
import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiService {
    public static final String GEMINI_PRO = "gemini-pro";

    private final GeminiInterface geminiInterface;
    private final AiRepository aiRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public GeminiResponse getCompletion(GeminiRequest request){
        return geminiInterface.getCompletion(GEMINI_PRO, request);
    }

    public String getAiResponse(String message){
        GeminiRequest geminiRequest = new GeminiRequest(message+", 답변을 최대한 간결하게 50자 이하로 해줘");
        System.out.println(geminiRequest.getContents().get(0));
//        System.out.println(geminiRequest.getGenerationConfig());
        GeminiResponse response = getCompletion(geminiRequest);
        System.out.println(response);
        String aiResult = response.getCandidates()
                .stream()
                .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                        .stream()
                        .findFirst()
                        .map(GeminiResponse.TextPart::getText))
                .orElse(null);
        return aiResult;
    }

    @Transactional
    public String getCompletion(String text, String username, UUID storeId){
        String aiResponseMessage = getAiResponse(text);
        Member member =  memberRepository.getByUsername(username);
        Optional<Store> store = storeRepository.findById(storeId);
        if (store.isEmpty()) {
            throw new ApplicationException(ErrorCode.INVALID_STORE);
        }
        // DB 저장
        aiRepository.save(AiData.AiData(store.get(), text, aiResponseMessage));
        return aiResponseMessage;
    }

    public List<AiSearchResponse> getDataFromUser(Ai ai) {
        Store store = ai.getStore();

        List<AiSearchResponse> aiList = aiRepository.findById(UUID.fromString(String.valueOf(ai.getId()))).stream()
                .map(s->AiSearchResponse.from(s))
                .toList();

        return aiList;
    }

    // 가게 리스트 출력
    public Page<AiSearchListResponse> getDataList(Pageable pageable) {

        return aiRepository.searchAi(pageable);
    }

    @Transactional
    public UUID delete(UUID aiId, Store store) {

        Ai ai = aiRepository.findById(aiId)
                .orElseThrow(()-> new ApplicationException(ErrorCode.INVALID_AI));

        if(!ai.getStore().getStoreId().equals(store.getStoreId())){
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }
        return aiId;
    }


}