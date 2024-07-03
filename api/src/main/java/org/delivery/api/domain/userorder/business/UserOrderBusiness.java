package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
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
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {
    private final UserOrderConverter userOrderConverter;
    private final StoreConverter storeConverter;

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;

    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;

    private final UserOrderProducer userOrderProducer;

    public UserOrderResponse order(User user, UserOrderRequest request){

        List<StoreMenuEntity> storeMenuEntityList = storeMenuService.getStoreMenuWithThrow(request.getStoreMenuIdList());

        List<UserOrderMenuEntity> orderMenuEntityList = userOrderMenuService.order(storeMenuEntityList, request.getCountList());
        UserOrderEntity orderEntity = userOrderService.order(user, orderMenuEntityList, request);

        try{ //비동기로 가맹점에 주문 알림
            userOrderProducer.sendOrder(orderEntity);
            orderEntity = userOrderService.orderSuccess(orderEntity);

            return userOrderConverter.toResponse(orderEntity);
        }catch (Exception e){
            return userOrderConverter.toResponse(orderEntity);
        }
    }

    public Page<UserOrderDetailResponse> currentOrders(User user, Pageable pageable) {
        var userOrderEntityList = userOrderService.currentOrders(user.getId(),pageable);

        var userOrderDetailRespnseList = userOrderEntityList.map(userOrderEntity->{

            var userOrderMenuEntityList = userOrderEntity.getOrderMenus();
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity->{
                var storeMenuEntity =  userOrderMenuEntity.getMenu();
                return storeMenuEntity;
            }).toList();

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeResponse(storeConverter.toResponse(userOrderEntity.getStore()))
                    .userOrderMenuResponseList(userOrderMenuConverter.toResponse(userOrderMenuEntityList, storeMenuEntityList))
                    .build();
        });

        return userOrderDetailRespnseList;
    }

    public Page<UserOrderDetailResponse> historyOrders(User user, Pageable pageable) {
        var userOrderEntityList = userOrderService.historyOrders(user.getId(),pageable);

        var userOrderDetailRespnseList = userOrderEntityList.map(userOrderEntity->{

            var userOrderMenuEntityList = userOrderEntity.getOrderMenus();
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity->{
                var storeMenuEntity =  userOrderMenuEntity.getMenu();
                return storeMenuEntity;
            }).toList();

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeResponse(storeConverter.toResponse(userOrderEntity.getStore()))
                    .userOrderMenuResponseList(userOrderMenuConverter.toResponse(userOrderMenuEntityList, storeMenuEntityList))
                    .build();
        });

        return userOrderDetailRespnseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.findOrderByIdAndUser(orderId,user.getId());

        var userOrderMenuEntityList = userOrderEntity.getOrderMenus();
        var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity->{
            var storeMenuEntity =  userOrderMenuEntity.getMenu();
            return storeMenuEntity;
        }).toList();

        var storeEntity = userOrderEntity.getStore();
        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .userOrderMenuResponseList(userOrderMenuConverter.toResponse(userOrderMenuEntityList, storeMenuEntityList))
                .build();
    }
}
