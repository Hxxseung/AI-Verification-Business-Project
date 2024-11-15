package com.sparta.aibusinessproject.domain.ai.service;


import com.sparta.aibusinessproject.domain.ai.dto.AiData;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchListResponse;
import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchResponse;
import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import com.sparta.aibusinessproject.domain.ai.gemini.GeminiInterface;
import com.sparta.aibusinessproject.domain.ai.gemini.GeminiRequest;
import com.sparta.aibusinessproject.domain.ai.gemini.GeminiResponse;
import com.sparta.aibusinessproject.domain.ai.repository.AiRepository;
import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiService {
    public static final String GEMINI_PRO = "gemini-pro";

    private final GeminiInterface geminiInterface;
    private final AiRepository aiRepository;


    public GeminiResponse getCompletion(GeminiRequest request){
        return geminiInterface.getCompletion(GEMINI_PRO, request);
    }

    public String getAiResponse(String message){
        GeminiRequest geminiRequest = new GeminiRequest(message+", 답변을 최대한 간결하게 50자 이하로 해줘");
        GeminiResponse response = getCompletion(geminiRequest);

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
    public String getCompletion(String text, Member member){
        String aiResponseMessage = getAiResponse(text);

        // DB 저장
        aiRepository.save(AiData.AiData(member, text, aiResponseMessage));
        return aiResponseMessage;
    }

    public List<AiSearchResponse> getDataFromUser(UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();

        List<AiSearchResponse> aiList = aiRepository.findByUserUserId(user.getUserId()).stream()
                .map(s->AiSearchResponse.from(s))
                .toList();

        return aiList;
    }

    // 가게 리스트 출력
    public Page<AiSearchListResponse> getDataList(Pageable pageable) {

        return aiRepository.searchAi(pageable);
    }

    @Transactional
    public UUID delete(UUID aiId, User user) {

        Ai ai = aiRepository.findById(aiId)
                .orElseThrow(()-> new ApplicationException(ErrorCode.INVALID_AI));

        if(!ai.getUser().getUserId().equals(user.getUserId())){
            throw new ApplicationException(ErrorCode.ACCESS_DENIED);
        }

        aiRepository.delete(ai.getId(),user.getUserId());
        return aiId;
    }


}