package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.List;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {
    private final UserOrderConverter userOrderConverter;
    private final StoreConverter storeConverter;
    private final StoreMenuConverter storeMenuConverter;

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;

    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;

    private final UserOrderProducer userOrderProducer;

    private final StoreService storeService;
    public UserOrderResponse order(User user, UserOrderRequest request){

        var storeMenuEntityList = request.getStoreMenuIdList().stream().map(
                it-> storeMenuService.getStoreMenuWithThrow(it)
        ).toList();

        var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);
        var savedEntity = userOrderService.order(userOrderEntity);

        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it-> {
                   var userOrderMenuEntity = userOrderMenuConverter.toEntity(savedEntity, it);
                   return userOrderMenuEntity;
                }).toList();

        userOrderMenuEntityList.forEach(it->{
            userOrderMenuService.order(it);
        });

        //비동기로 가맹점에 주문 알림
        userOrderProducer.sendOrder(savedEntity);

        return userOrderConverter.toResponse(savedEntity);
    }

    public List<UserOrderDetailResponse> currentOrders(User user) {
        var userOrderEntityList = userOrderService.currentOrders(user.getId());

        var userOrderDetailRespnseList = userOrderEntityList.stream().map(userOrderEntity->{

            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity->{
                var storeMenuEntity =  storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            }).toList();

            // 사용자가 주문한 스토어 todo 리팩토링 필요
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());
            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).toList();

        return userOrderDetailRespnseList;
    }

    public List<UserOrderDetailResponse> historyOrders(User user) {
        var userOrderEntityList = userOrderService.historyOrders(user.getId());

        var userOrderDetailRespnseList = userOrderEntityList.stream().map(userOrderEntity->{

            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity->{
                var storeMenuEntity =  storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            }).toList();

            // 사용자가 주문한 스토어 todo 리팩토링 필요
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());
            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).toList();

        return userOrderDetailRespnseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId,user.getId());

        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity->{
            var storeMenuEntity =  storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
            return storeMenuEntity;
        }).toList();

        // 사용자가 주문한 스토어 todo 리팩토링 필요
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());
        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .build();
    }
}
