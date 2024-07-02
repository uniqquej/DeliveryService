package org.delivery.api.domain.review.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.review.controller.model.ReviewRegisterRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.review.controller.model.ReviewUpdateRequest;
import org.delivery.api.domain.review.converter.ReviewConverter;
import org.delivery.api.domain.review.service.ReviewService;
import org.delivery.api.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Business
@RequiredArgsConstructor
public class ReviewBusiness {
    private final ReviewService reviewService;
    private final ReviewConverter reviewConverter;

    public ReviewResponse register(ReviewRegisterRequest request, User user){
        var reviewEntity = reviewConverter.toEntity(request);
        var savedEntity = reviewService.register(reviewEntity, user, request.getUserOrderId(), request.getStoreId());
        return reviewConverter.toResponse(savedEntity);
    }

    public Page<ReviewResponse> searchByStoreId(Long storeId, Pageable pageable){
        var reviewEntityList = reviewService.searchByStoreId(storeId,pageable);
        return reviewEntityList.map(reviewConverter::toResponse);
    }

    public Page<ReviewResponse> searchByUserId(Long userId, Pageable pageable){
        var reviewEntityList = reviewService.searchByUserId(userId, pageable);
        return reviewEntityList.map(reviewConverter::toResponse);
    }

    public ReviewResponse getReview(Long reviewId) {
        return reviewConverter.toResponse(reviewService.getReviewWithThrow(reviewId));
    }

    public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request, User user) {
        var newReviewEntity = reviewService.updateReview(reviewId, request ,user);
        return reviewConverter.toResponse(newReviewEntity);
    }
}
