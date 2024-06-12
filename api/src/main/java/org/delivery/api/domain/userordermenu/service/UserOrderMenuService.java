package org.delivery.api.domain.userordermenu.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional
public class UserOrderMenuService {
    private final UserOrderMenuRepository userOrderMenuRepository;


    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId){
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }

    public List<UserOrderMenuEntity> order(List<StoreMenuEntity> storeMenuEntityList, List<Long> countList ){
        AtomicInteger index = new AtomicInteger(0);

        var userOrderMenuEntityList = storeMenuEntityList.stream().map(storeMenuEntity -> {
            int currentIdx = index.getAndIncrement();
            var orderMenuEntity = UserOrderMenuEntity.createUserOrderMenu(storeMenuEntity, countList.get(currentIdx));
            return orderMenuEntity;
        }).toList();

        return userOrderMenuEntityList;
    }
}
