package com.sparta.aibusinessproject.domain.review.entity;

import com.sparta.aibusinessproject.domain.member.entity.BaseEntity;
import com.sparta.aibusinessproject.domain.order.entity.Orders;
import com.sparta.aibusinessproject.domain.review.dto.request.CreateReviewRequest;
import com.sparta.aibusinessproject.domain.review.dto.request.ModifyReviewRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Orders order;

    @Min(value = 1, message = "평점은 1점 이상이어야 합니다")
    @Max(value = 5, message = "평점은 5점 이하여야 합니다")
    private Integer score;

    @Size(max = 255, message = "리뷰는 255자를 초과할 수 없습니다")
    private String contents;

    public static Review create(CreateReviewRequest request, Orders order) {
        return Review.builder()
                .order(order)
                .score(request.score())
                .contents(request.contents())
                .build();
    }

    public void update(ModifyReviewRequest request) {
        Optional.ofNullable(request.contents()).ifPresent(value -> this.contents = value);
        Optional.ofNullable(request.score()).ifPresent(value -> this.score = value);
    }

    public void delete() {
        // todo: soft delete에 대한 고민 필요
        this.deletedAt = LocalDateTime.now();
    }

}
