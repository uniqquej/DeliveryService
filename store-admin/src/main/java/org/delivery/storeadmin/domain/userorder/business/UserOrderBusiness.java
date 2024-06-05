package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuConverter userOrderMenuConverter;

    private final UserOrderMenuService userOrderMenuService;
    private final UserOrderConverter userOrderConverter;

    private final SseConnectionPool sseConnectionPool;

    public void pushUserOrder(UserOrderMessage userOrderMessage){
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(()-> new RuntimeException("사용자 주문 내역 없음"));

        var userOrderMenuList = userOrderMenuService.getUserOrderMenu(userOrderMessage.getUserOrderId());
        var storeMenuList = userOrderMenuList.stream().map(
                it-> {
                    return storeMenuService.getStoreMenuWithThrow(it.getStoreMenuId());
                }).toList();

        var userOrderMenuResponseList = userOrderMenuConverter.toResponse(userOrderMenuList, storeMenuList);

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .userOrderMenuResponseList(userOrderMenuResponseList)
                .build();

        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        userConnection.sendMessage(push);
    }
}
