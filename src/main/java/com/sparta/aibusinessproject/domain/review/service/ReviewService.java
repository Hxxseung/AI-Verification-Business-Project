package com.sparta.aibusinessproject.domain.review.service;

import com.sparta.aibusinessproject.domain.order.entity.Orders;
import com.sparta.aibusinessproject.domain.order.repository.OrdersRepository;
import com.sparta.aibusinessproject.domain.review.dto.request.CreateReviewRequest;
import com.sparta.aibusinessproject.domain.review.entity.Review;
import com.sparta.aibusinessproject.domain.review.repository.ReviewRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrdersRepository ordersRepository;
    public UUID createReview(CreateReviewRequest request) {
        Orders order = ordersRepository.getById(request.orderId());
        Review review = reviewRepository.save(Review.create(request, order));
        return review.getId();
    }
}
