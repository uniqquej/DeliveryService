package org.delivery.storeadmin.domain.userorder.converter;

import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.stereotype.Component;

@Component
public class UserOrderConverter {
    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity){
        return UserOrderResponse.builder()
                .id(userOrderEntity.getId())
                .userId(userOrderEntity.getId())
                .storeId(userOrderEntity.getStoreId())
                .status(userOrderEntity.getStatus())
                .amount(userOrderEntity.getAmount())
                .orderedAt(userOrderEntity.getOrderedAt())
                .acceptedAt(userOrderEntity.getAcceptedAt())
                .cookingStartedAt(userOrderEntity.getCookingStartedAt())
                .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
                .receivedAt(userOrderEntity.getReceivedAt())
                .cancelledAt(userOrderEntity.getCancelledAt())
                .build();
    }
}
