package org.delivery.api.domain.userordermenu.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.userordermenu.controller.model.UserOrderMenuResponse;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Converter
public class UserOrderMenuConverter {
    public UserOrderMenuEntity toEntity(UserOrderEntity userOrderEntity, StoreMenuEntity storeMenuEntity, Long count){
        return UserOrderMenuEntity.builder()
                .userOrder(userOrderEntity)
                .menu(storeMenuEntity)
                .count(count)
                .build();
    }

    public UserOrderMenuResponse toResponse(UserOrderMenuEntity userOrderMenuEntity,StoreMenuEntity storeMenuEntity)  {
        return Optional.ofNullable(userOrderMenuEntity)
                .map(it->{
                    return UserOrderMenuResponse.builder()
                            .price(storeMenuEntity.getPrice())
                            .menuName(storeMenuEntity.getName())
                            .count(userOrderMenuEntity.getCount())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public List<UserOrderMenuResponse> toResponse(List<UserOrderMenuEntity> userOrderMenuEntityList,List<StoreMenuEntity> storeMenuEntityList) {
        AtomicInteger index = new AtomicInteger(0);
        return userOrderMenuEntityList.stream()
                .map(
                        it-> {
                            int currentIndex = index.getAndIncrement();
                            return toResponse(it, storeMenuEntityList.get(currentIndex));
                        }
                )
                .toList();
    }
}
