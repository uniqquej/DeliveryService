package org.delivery.api.domain.review.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.review.controller.model.ReviewRegisterRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.user.UserEntity;

@Converter
public class ReviewConverter {

    public ReviewEntity toEntity(ReviewRegisterRequest request){
        return ReviewEntity.builder()
                .content(request.getContent())
                .star(request.getStar())
                .build();
    }

    public ReviewResponse toResponse(ReviewEntity reviewEntity){
        return ReviewResponse.builder()
                .id(reviewEntity.getId())
                .content(reviewEntity.getContent())
                .star(reviewEntity.getStar())
                .status(reviewEntity.getStatus())
                .userId(reviewEntity.getUser().getId())
                .userOrderId(reviewEntity.getUserOrder().getId())
                .storeId(reviewEntity.getStore().getId())
                .registeredAt(reviewEntity.getRegisteredAt())
                .updatedAt(reviewEntity.getUpdatedAt())
                .build();
    }

}
