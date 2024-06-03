package org.delivery.api.domain.review.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.review.controller.model.ReviewRegisterRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.review.controller.model.ReviewUpdateRequest;
import org.delivery.api.domain.review.converter.ReviewConverter;
import org.delivery.api.domain.review.service.ReviewService;
import org.delivery.api.domain.user.model.User;

import java.util.List;

@Business
@RequiredArgsConstructor
public class ReviewBusiness {
    private final ReviewService reviewService;
    private final ReviewConverter reviewConverter;

    public ReviewResponse register(ReviewRegisterRequest request, User user){
        var reviewEntity = reviewConverter.toEntity(request, user);
        var savedEntity = reviewService.register(reviewEntity);
        return reviewConverter.toResponse(savedEntity);
    }

    public List<ReviewResponse> searchByStoreId(Long storeId){
        var reviewEntityList = reviewService.searchByStoreId(storeId);
        return reviewEntityList.stream().map(reviewConverter::toResponse).toList();
    }

    public List<ReviewResponse> searchByUserId(Long userId){
        var reviewEntityList = reviewService.searchByUserId(userId);
        return reviewEntityList.stream().map(reviewConverter::toResponse).toList();
    }

    public ReviewResponse getReview(Long reviewId) {
        return reviewConverter.toResponse(reviewService.getReviewWithThrow(reviewId));
    }

    public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request, User user) {
        var newReviewEntity = reviewService.updateReview(reviewId, request ,user);
        return reviewConverter.toResponse(newReviewEntity);
    }
}
