package com.sparta.aibusinessproject.domain.review.controller;

import com.sparta.aibusinessproject.domain.review.dto.request.CreateReviewRequest;
import com.sparta.aibusinessproject.domain.review.dto.response.CreateReviewResponse;
import com.sparta.aibusinessproject.domain.review.service.ReviewService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Response<CreateReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
        return Response.success(CreateReviewResponse.from(reviewService.createReview(request)));
    }
}
