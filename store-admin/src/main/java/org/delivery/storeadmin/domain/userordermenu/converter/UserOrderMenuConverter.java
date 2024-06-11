package org.delivery.storeadmin.domain.userordermenu.converter;

import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.storeadmin.domain.userordermenu.controller.model.UserOrderMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserOrderMenuConverter {
    public List<UserOrderMenuResponse> toResponse(
            List<UserOrderMenuEntity> userOrderMenuEntityList,
            List<StoreMenuEntity> storeMenuEntityList
    ){
        AtomicInteger index = new AtomicInteger(0);
        return storeMenuEntityList.stream().map(
                storeMenuEntity-> {
                    int currentIndex = index.getAndIncrement();
                    var storeMenuResponse = UserOrderMenuResponse.builder()
                            .menuName(storeMenuEntity.getName())
                            .amount(storeMenuEntity.getPrice())
                            .count(userOrderMenuEntityList.get(currentIndex).getCount())
                            .build();
                    return storeMenuResponse;
                }
        ).toList();
    }
}
