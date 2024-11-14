package com.sparta.aibusinessproject.domain.ai.service;


import com.sparta.aibusinessproject.domain.ai.repository.AiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {
    private final AiRepository aiRepository;
}
