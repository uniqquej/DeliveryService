package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
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

    public UserOrderEntity getUserOrderWithOutStatusWithThrow(Long id, Long userId){
        var userOrder = userOrderRepository.findAllByIdAndUserId(id, userId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));

        return userOrder;
    }

    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId){
        var userOrder = userOrderRepository.findByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));

        return userOrder;
    }

    public List<UserOrderEntity> getUserOrderList(Long userId){
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }

    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    public List<UserOrderEntity> currentOrders(Long userId){
        // 현재 진행 중인 주문
        var statusList = List.of(UserOrderStatus.ORDER,UserOrderStatus.COOKING,UserOrderStatus.ACCEPT,UserOrderStatus.DELIVERY) ;
        return getUserOrderList(userId,statusList);
    }

    public List<UserOrderEntity> historyOrders(Long userId){
        //완료된 주문
        var statusList = List.of(UserOrderStatus.RECEIVE) ;
        return getUserOrderList(userId,statusList);
    }

    public UserOrderEntity order(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it->{
                    it.setOrderedAt(LocalDateTime.now());
                    it.setStatus(UserOrderStatus.ORDER);
                    return userOrderRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public UserOrderEntity setStatus(UserOrderEntity userOrderEntity, UserOrderStatus status){
        userOrderEntity.setStatus(status);
        return userOrderRepository.save(userOrderEntity);
    }

    public UserOrderEntity accept(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it->{
                    it.setAcceptedAt(LocalDateTime.now());
                    return setStatus(it, UserOrderStatus.ACCEPT);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public UserOrderEntity cooking(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it->{
                    it.setCookingStartedAt(LocalDateTime.now());
                    return setStatus(it, UserOrderStatus.COOKING);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public UserOrderEntity delivery(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it->{
                    it.setDeliveryStartedAt(LocalDateTime.now());
                    return setStatus(it, UserOrderStatus.DELIVERY);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public UserOrderEntity receive(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it->{
                    it.setReceivedAt(LocalDateTime.now());
                    return setStatus(it, UserOrderStatus.RECEIVE);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

}
