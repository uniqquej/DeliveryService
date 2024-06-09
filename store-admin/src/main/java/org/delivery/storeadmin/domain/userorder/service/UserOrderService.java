package org.delivery.storeadmin.domain.userorder.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;

    public Optional<UserOrderEntity> getUserOrder(Long userOrderId){
        return userOrderRepository.findById(userOrderId);
    }

    @Transactional
    public UserOrderEntity updateOrderState(Long userOrderId){
        var userOrderEntity = getUserOrder(userOrderId).get();

        if(userOrderEntity.getAcceptedAt()==null){
            userOrderEntity.setAcceptedAt(LocalDateTime.now());
            userOrderEntity.setStatus(UserOrderStatus.ACCEPT);
        }
        else if(userOrderEntity.getCookingStartedAt()==null){
            userOrderEntity.setCookingStartedAt(LocalDateTime.now());
            userOrderEntity.setStatus(UserOrderStatus.COOKING);
        }
        else if(userOrderEntity.getDeliveryStartedAt()==null){
            userOrderEntity.setDeliveryStartedAt(LocalDateTime.now());
            userOrderEntity.setStatus(UserOrderStatus.DELIVERY);
        }
        return userOrderEntity;
    }

    public List<UserOrderEntity> getOrdersByStoreId(Long storeId){
        List<UserOrderStatus> statusList = List.of(
                UserOrderStatus.ORDER,
                UserOrderStatus.COOKING,
                UserOrderStatus.ACCEPT,
                UserOrderStatus.DELIVERY
        );

        return userOrderRepository.findAllByStoreIdAndStatusInOrderByIdDesc(storeId, statusList);
    }
}
