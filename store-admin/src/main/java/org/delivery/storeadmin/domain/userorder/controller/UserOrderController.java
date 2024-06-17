package org.delivery.storeadmin.domain.userorder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.userorder.business.UserOrderBusiness;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/store-order")
@RequiredArgsConstructor
@Slf4j(topic = "[8081]user Order Controller")
public class UserOrderController {
    private final UserOrderBusiness userOrderBusiness;

    @GetMapping(path = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String orderInfo(
            @AuthenticationPrincipal UserSession userSession,
            @RequestParam(name = "status", required = false) String status,
            Model model
    ){
        List<UserOrderResponse> orderList;
        if(status != null ) {
            if (status.equals("complete"))
                orderList = userOrderBusiness.getOrdersByStatus(userSession.getStoreId(), UserOrderStatus.RECEIVE);
            else
                orderList = userOrderBusiness.getOrdersByStatus(userSession.getStoreId(), UserOrderStatus.CANCELLED);
        }
        else orderList = userOrderBusiness.getOrdersByStoreId(userSession.getStoreId());

        model.addAttribute("orderList",orderList);

        return "order/orderList";
    }

    @PostMapping("/id/{orderId}")
    public @ResponseBody ResponseEntity updateOrder(
            @PathVariable(name = "orderId") Long orderId
    ){
        userOrderBusiness.updateOrderState(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @PostMapping("")
    public @ResponseBody ResponseEntity deleteOrder(
            @RequestParam(name = "id") Long orderId
    ){
        userOrderBusiness.deleteOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping("/id/{orderId}")
    public String detailOrder(
            @PathVariable(name = "orderId") Long orderId,
            Model model
    ){
        var detailResponse = userOrderBusiness.orderDetail(orderId);
        model.addAttribute("order",detailResponse.getUserOrderResponse());
        model.addAttribute("orderMenuList", detailResponse.getUserOrderMenuResponseList());
       return "order/orderDetail";
    }
}
