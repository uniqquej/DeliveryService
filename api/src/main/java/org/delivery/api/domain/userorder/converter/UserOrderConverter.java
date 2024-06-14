package org.delivery.api.domain.userorder.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.user.UserEntity;
import org.delivery.db.userorder.UserOrderEntity;

@Converter
public class UserOrderConverter {

    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity) {
        return UserOrderResponse.builder()
                .id(userOrderEntity.getId())
                .storeId(userOrderEntity.getStore().getId())
                .hasReview(userOrderEntity.hasReview())
                .address(userOrderEntity.getAddress())
                .status(userOrderEntity.getStatus())
                .totalPrice(userOrderEntity.getTotalPrice())
                .orderedAt(userOrderEntity.getOrderedAt())
                .acceptedAt(userOrderEntity.getAcceptedAt())
                .cookingStartedAt(userOrderEntity.getCookingStartedAt())
                .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
                .receivedAt(userOrderEntity.getReceivedAt())
                .build();
    }
}
