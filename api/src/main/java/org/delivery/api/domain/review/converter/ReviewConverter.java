package org.delivery.api.domain.review.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.review.controller.model.ReviewRegisterRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.review.ReviewEntity;

@Converter
public class ReviewConverter {

    public ReviewEntity toEntity(ReviewRegisterRequest request, User user){
        return ReviewEntity.builder()
                .content(request.getContent())
                .star(request.getStar())
                .userId(user.getId())
                .storeId(request.getStoreId())
                .userOrderId(request.getUserOrderId())
                .build();
    }

    public ReviewResponse toResponse(ReviewEntity reviewEntity){
        return ReviewResponse.builder()
                .id(reviewEntity.getId())
                .content(reviewEntity.getContent())
                .star(reviewEntity.getStar())
                .status(reviewEntity.getStatus())
                .userId(reviewEntity.getUserId())
                .userOrderId(reviewEntity.getUserOrderId())
                .storeId(reviewEntity.getStoreId())
                .registeredAt(reviewEntity.getRegisteredAt())
                .updatedAt(reviewEntity.getUpdatedAt())
                .build();
    }

}
