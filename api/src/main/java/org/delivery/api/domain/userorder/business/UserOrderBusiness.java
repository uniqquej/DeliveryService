package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {
    private final UserOrderConverter userOrderConverter;
    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;

    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
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
        return userOrderConverter.toResponse(savedEntity);
    }

}
