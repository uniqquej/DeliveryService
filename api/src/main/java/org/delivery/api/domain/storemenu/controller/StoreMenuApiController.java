package org.delivery.api.domain.storemenu.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.user.enums.UserRole;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/api/store-menu")
@RequiredArgsConstructor
public class StoreMenuApiController {
    private final StoreMenuBusiness storeMenuBusiness;

    @GetMapping("/search")
    public Api<List<StoreMenuResponse>> search(
            @RequestParam(required = false)
            Long storeId
    ){
        var response = storeMenuBusiness.search(storeId);
        return Api.OK(response);
    }

    @PostMapping("/register")
    public Api<StoreMenuResponse> register(
            @RequestBody @Valid Api<StoreMenuRegisterRequest> request,
            @Parameter(hidden = true) @UserSession User user
    ){
        if(user.getRole() != UserRole.ADMIN)
            throw new IllegalArgumentException("잘못된 접근");

        var response = storeMenuBusiness.register(request.getBody());
        return Api.OK(response);
    }

}
