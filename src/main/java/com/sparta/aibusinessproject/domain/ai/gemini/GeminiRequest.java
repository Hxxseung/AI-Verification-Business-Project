package com.sparta.aibusinessproject.domain.ai.gemini;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class GeminiRequest {

    private List<Content> contents;     // 객체의 요청의 내용 담을 공간
//    private GenerationConfig generationConfig;

    /* Json 형식을 맞추기 위해 생성자에게 구조 맞춰줌
        Request Body
        {
            "contents": [
                {
                    "parts":
                        {
                            "text": "치킨집 소개글 작성해줘"
                        }

                }
             ],
            "generationConfig": {
                "candidate_count":1,
                "max_output_tokens":1000,
                "temperature": 0.7
            }
        }

     */


    public GeminiRequest(String text) {
        Part part = new TextPart(text);
        Content content = new Content(Collections.singletonList(part));
        this.contents = Arrays.asList(content);
//        this.generationConfig = new GenerationConfig(1,1000,0.7);
    }


    @Getter
    @AllArgsConstructor
    private static class Content {
        private List<Part> parts;
    }

    interface Part {
    }

    @Getter
    @AllArgsConstructor
    private static class TextPart implements Part {
        public String text;
    }
}