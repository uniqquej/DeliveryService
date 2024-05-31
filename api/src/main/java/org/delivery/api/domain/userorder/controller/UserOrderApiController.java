package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
