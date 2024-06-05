package org.delivery.api.domain.userorder.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {

    public UserOrderEntity toEntity(User user, List<StoreMenuEntity> storeMenuEntityList, List<Long> countList, Long storeId){
        var totalAmount = BigDecimal.ZERO;

        if(storeMenuEntityList.size()!=countList.size())
            throw new IllegalArgumentException("잘못된 입력");

        for(int i=0;i<storeMenuEntityList.size();i++){
            StoreMenuEntity storeMenuEntity = storeMenuEntityList.get(i);
            Long count = countList.get(i);
            BigDecimal amount = storeMenuEntity.getAmount().multiply(BigDecimal.valueOf(count));
            totalAmount = totalAmount.add(amount);
        }

        return UserOrderEntity.builder()
                .userId(user.getId())
                .storeId(storeId)
                .amount(totalAmount)
                .build();
    }

    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity) {
        return UserOrderResponse.builder()
                .id(userOrderEntity.getId())
                .storeId(userOrderEntity.getStoreId())
                .status(userOrderEntity.getStatus())
                .amount(userOrderEntity.getAmount())
                .orderedAt(userOrderEntity.getOrderedAt())
                .acceptedAt(userOrderEntity.getAcceptedAt())
                .cookingStartedAt(userOrderEntity.getCookingStartedAt())
                .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
                .receivedAt(userOrderEntity.getReceivedAt())
                .build();
    }
}
