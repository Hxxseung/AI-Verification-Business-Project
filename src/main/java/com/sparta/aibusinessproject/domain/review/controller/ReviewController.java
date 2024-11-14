package com.sparta.aibusinessproject.domain.review.controller;

import com.sparta.aibusinessproject.domain.review.dto.request.CreateReviewRequest;
import com.sparta.aibusinessproject.domain.review.dto.request.ModifyReviewRequest;
import com.sparta.aibusinessproject.domain.review.dto.response.CreateReviewResponse;
import com.sparta.aibusinessproject.domain.review.dto.response.GetReviewResponse;
import com.sparta.aibusinessproject.domain.review.entity.Review;
import com.sparta.aibusinessproject.domain.review.service.ReviewService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{reviewId}")
    public Response<GetReviewResponse> getReview(@PathVariable UUID reviewId) {
        return Response.success(GetReviewResponse.of(reviewService.getReview(reviewId)));
    }

    @PutMapping()
    public Response<GetReviewResponse> modifyReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ModifyReviewRequest request) {
        return Response.success(GetReviewResponse.of(reviewService.modifyReview(userDetails.getUsername(), request)));
    }
}
