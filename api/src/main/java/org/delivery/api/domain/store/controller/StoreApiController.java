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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreApiController {
    private final StoreBusiness storeBusiness;
    @GetMapping("/detail")
    public Api<StoreDetailResponse> detail(
            @RequestParam(required = false)
            Long storeId,
            @Parameter(hidden = true)
            @UserSession User user
    ){
        var response = storeBusiness.storeDetail(storeId, user);
        return Api.OK(response);
    }
}
