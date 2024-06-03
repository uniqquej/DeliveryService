package org.delivery.api.domain.store.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreDetailResponse;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.user.enums.UserRole;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreApiController {
    private final StoreBusiness storeBusiness;

    @GetMapping("/search")
    public Api<List<StoreResponse>> search(
            @RequestParam(required = false)
            StoreCategory category
    ){
        var response = storeBusiness.searchCategory(category);
        return Api.OK(response);
    }

    @GetMapping("/detail")
    public Api<StoreDetailResponse> detail(
            @RequestParam(required = false)
            Long storeId
    ){
        var response = storeBusiness.storeDetail(storeId);
        return Api.OK(response);
    }

    @PostMapping("/register")
    public Api<StoreResponse> register(
            @RequestBody @Valid Api<StoreRegisterRequest> request,
            @Parameter(hidden = true) @UserSession User user
    ){
        if(user.getRole() != UserRole.ADMIN)
            throw new IllegalArgumentException("잘못된 접근");

        var response = storeBusiness.register(request.getBody());
        return Api.OK(response);
    }

}
