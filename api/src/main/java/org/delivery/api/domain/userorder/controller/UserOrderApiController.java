package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-order")
@RequiredArgsConstructor
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    @PostMapping("")
    public Api<UserOrderResponse> order(
            @RequestBody @Valid Api<UserOrderRequest> request,

            @Parameter(hidden = true)
            @UserSession User user
    ){
        var response = userOrderBusiness.order(user, request.getBody());
        return Api.OK(response);
    }

    @GetMapping("/current")
    public Api<Page<UserOrderDetailResponse>> currentOrders(
            @Parameter(hidden = true)
            @UserSession User user,
            @PageableDefault(page=1) Pageable pageable
    ){
        var response = userOrderBusiness.currentOrders(user,pageable);
        return Api.OK(response);
    }

    @GetMapping("/history")
    public Api<Page<UserOrderDetailResponse>> historyOrders(
            @Parameter(hidden = true)
            @UserSession User user,
            @PageableDefault(page = 1) Pageable pageable
    ){
        var response = userOrderBusiness.historyOrders(user,pageable);
        return Api.OK(response);
    }

    @GetMapping("/id/{orderId}")
    public Api<UserOrderDetailResponse> read(
            @Parameter(hidden = true)
            @UserSession User user,

            @PathVariable Long orderId
    ){
        var response = userOrderBusiness.read(user, orderId);
        return Api.OK(response);
    }

}
